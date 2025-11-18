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

/**
 * A server that contains all {@link TerminalContext}.
 */
final class BasicTerminalServerContext<T extends TerminalContext> implements TerminalServerContext<T> {

    static <T extends TerminalContext> BasicTerminalServerContext<T> with(final Function<EnvironmentContext, T> environmentContextToTerminalContext) {
        return new BasicTerminalServerContext<>(
            Objects.requireNonNull(environmentContextToTerminalContext, "environmentContextToTerminalContext")
        );
    }

    private BasicTerminalServerContext(final Function<EnvironmentContext, T> environmentContextToTerminalContext) {
        super();
        this.environmentContextToTerminalContext = Objects.requireNonNull(environmentContextToTerminalContext, "environmentContextToTerminalContext");
    }

    @Override
    public T createTerminalContext(final EnvironmentContext context) {
        Objects.requireNonNull(context, "context");

        final T terminalContext = this.environmentContextToTerminalContext.apply(
            context
        );

        this.terminalIdToTerminalContext.put(
            terminalContext.terminalId(),
            terminalContext
        );

        return terminalContext;
    }

    private final Function<EnvironmentContext, T> environmentContextToTerminalContext;

    @Override
    public Optional<T> terminalContext(final TerminalId id) {
        Objects.requireNonNull(id, "id");

        return Optional.ofNullable(
            this.terminalIdToTerminalContext.get(id)
        );
    }

    @Override
    public TerminalServerContext<T> removeTerminalContext(final TerminalId id) {
        Objects.requireNonNull(id, "id");

        this.terminalIdToTerminalContext.remove(id);
        return this;
    }

    private final Map<TerminalId, T> terminalIdToTerminalContext = Maps.concurrent();

    // Object...........................................................................................................

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
