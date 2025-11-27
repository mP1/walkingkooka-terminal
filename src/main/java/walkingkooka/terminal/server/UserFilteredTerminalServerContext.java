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

import walkingkooka.environment.EnvironmentContext;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.terminal.TerminalContext;
import walkingkooka.terminal.TerminalId;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A {@link TerminalServerContext} that uses a {@link Predicate} to filter all operations.
 */
final class UserFilteredTerminalServerContext implements TerminalServerContext {

    static UserFilteredTerminalServerContext with(final Predicate<Optional<EmailAddress>> filter,
                                                  final TerminalServerContext context) {
        return new UserFilteredTerminalServerContext(
            Objects.requireNonNull(filter, "filter"),
            Objects.requireNonNull(context, "context")
        );
    }

    private UserFilteredTerminalServerContext(final Predicate<Optional<EmailAddress>> filter,
                                              final TerminalServerContext context) {
        this.filter = filter;
        this.context = context;
    }

    // TerminalServerContext............................................................................................

    @Override
    public TerminalContext addTerminalContext(final Function<TerminalId, TerminalContext> terminalContextFactory) {
        Objects.requireNonNull(terminalContextFactory, "terminalContextFactory");

        return this.verifyTerminalContextUser(
            this.context.addTerminalContext(terminalContextFactory),
            "Added"
        );
    }

    @Override
    public TerminalContext createTerminalContext(final EnvironmentContext context) {
        Objects.requireNonNull(context, "context");

        // TODO maybe should verify EnvironmentContext#user is current user

        return this.verifyTerminalContextUser(
            this.context.createTerminalContext(context),
            "Created"
        );
    }

    private TerminalContext verifyTerminalContextUser(final TerminalContext terminalContext,
                                                      final String action) {
        final Optional<EmailAddress> user = terminalContext.user();
        if (this.filter.test(user)) {
            return terminalContext;
        }

        throw new IllegalArgumentException(action + " TerminalContext belongs to different user");
    }

    @Override
    public Optional<TerminalContext> terminalContext(final TerminalId id) {
        return this.context.terminalContext(id)
            .filter(c -> this.filter.test(c.user()));
    }

    private final Predicate<Optional<EmailAddress>> filter;

    @Override
    public TerminalServerContext removeTerminalContext(final TerminalId id) {
        final Optional<TerminalContext> terminalContext = this.terminalContext(id);
        if (terminalContext.isPresent()) {
            this.context.removeTerminalContext(id);
        }
        return this;
    }

    private final TerminalServerContext context;

    // Object...........................................................................................................

    @Override
    public String toString() {
        return this.context.toString();
    }
}
