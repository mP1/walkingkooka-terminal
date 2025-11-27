/*
 * Copyright 2025 Miroslav Pokorny (github.com/mP1)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package walkingkooka.terminal.server;

import walkingkooka.collect.map.Maps;
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.terminal.TerminalContext;
import walkingkooka.terminal.TerminalId;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A server that contains all {@link TerminalContext}.
 */
final class BasicTerminalServerContext implements TerminalServerContext {

    static BasicTerminalServerContext with(final Supplier<TerminalId> nextTerminalId,
                                           final Function<EnvironmentContext, TerminalContext> environmentContextToTerminalContext) {
        return new BasicTerminalServerContext(
            Objects.requireNonNull(nextTerminalId, "nextTerminalId"),
            Objects.requireNonNull(environmentContextToTerminalContext, "environmentContextToTerminalContext")
        );
    }

    private BasicTerminalServerContext(final Supplier<TerminalId> nextTerminalId,
                                       final Function<EnvironmentContext, TerminalContext> environmentContextToTerminalContext) {
        super();

        this.nextTerminalId = nextTerminalId;
        this.environmentContextToTerminalContext = Objects.requireNonNull(environmentContextToTerminalContext, "environmentContextToTerminalContext");
    }

    @Override
    public TerminalContext addTerminalContext(final Function<TerminalId, TerminalContext> terminalContextFactory) {
        Objects.requireNonNull(terminalContextFactory, "terminalContextFactory");

        final TerminalId terminalId = this.nextTerminalId.get();
        final TerminalContext terminalContext = terminalContextFactory.apply(terminalId);
        final TerminalId terminalContextId = terminalContext.terminalId();

        if (false == terminalId.equals(terminalContextId)) {
            throw new IllegalStateException("TerminalContext:TerminalId different " + terminalContextId + " from given " + terminalId);
        }

        this.saveTerminalContext(terminalContext);
        return terminalContext;
    }

    private final Supplier<TerminalId> nextTerminalId;

    @Override
    public TerminalContext createTerminalContext(final EnvironmentContext context) {
        Objects.requireNonNull(context, "context");

        final TerminalContext terminalContext = this.environmentContextToTerminalContext.apply(
            context
        );

        this.saveTerminalContext(terminalContext);
        return terminalContext;
    }

    private void saveTerminalContext(final TerminalContext context) {
        final TerminalId terminalId = context.terminalId();

        final Object previous = this.terminalIdToTerminalContext.putIfAbsent(
            terminalId,
            context
        );
        if (null != previous) {
            throw new IllegalStateException("TerminalContext created with duplicate TerminalId: " + terminalId);
        }
    }

    private final Function<EnvironmentContext, TerminalContext> environmentContextToTerminalContext;

    @Override
    public Optional<TerminalContext> terminalContext(final TerminalId id) {
        Objects.requireNonNull(id, "id");

        return Optional.ofNullable(
            this.terminalIdToTerminalContext.get(id)
        );
    }

    @Override
    public TerminalServerContext removeTerminalContext(final TerminalId id) {
        Objects.requireNonNull(id, "id");

        this.terminalIdToTerminalContext.remove(id);
        return this;
    }

    private final Map<TerminalId, TerminalContext> terminalIdToTerminalContext = Maps.concurrent();

    // Object...........................................................................................................

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
