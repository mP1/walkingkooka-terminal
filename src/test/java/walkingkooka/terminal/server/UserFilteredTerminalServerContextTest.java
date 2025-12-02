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

import org.junit.jupiter.api.Test;
import walkingkooka.ToStringTesting;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.predicate.Predicates;
import walkingkooka.terminal.FakeTerminalContext;
import walkingkooka.terminal.TerminalContext;
import walkingkooka.terminal.TerminalId;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class UserFilteredTerminalServerContextTest implements TerminalServerContextTesting<UserFilteredTerminalServerContext>,
    ToStringTesting<UserFilteredTerminalServerContext> {

    private final static Optional<EmailAddress> USER = Optional.of(
        EmailAddress.parse("user@example.com")
    );

    private final static TerminalId TERMINAL_ID = TerminalId.with(1);

    private final static TerminalContext TERMINAL_CONTEXT = new FakeTerminalContext() {

        @Override
        public TerminalId terminalId() {
            return TERMINAL_ID;
        }

        @Override
        public Optional<EmailAddress> user() {
            return USER;
        }

        @Override
        public String toString() {
            return "TerminalContext " + USER;
        }
    };

    private final static Optional<EmailAddress> DIFFERENT_USER = Optional.of(
        EmailAddress.parse("different@example.com")
    );

    private final static TerminalId DIFFERENT_TERMINAL_ID = TerminalId.with(2);

    private final static TerminalContext DIFFERENT_TERMINAL_CONTEXT = new FakeTerminalContext() {

        @Override
        public TerminalId terminalId() {
            return DIFFERENT_TERMINAL_ID;
        }

        @Override
        public Optional<EmailAddress> user() {
            return DIFFERENT_USER;
        }

        @Override
        public String toString() {
            return "TerminalContext " + DIFFERENT_USER;
        }
    };

    // with.............................................................................................................

    @Test
    public void testWithNullFilterFails() {
        assertThrows(
            NullPointerException.class,
            () -> UserFilteredTerminalServerContext.with(
                null,
                TerminalServerContexts.fake()
            )
        );
    }

    @Test
    public void testWithNullContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> UserFilteredTerminalServerContext.with(
                Predicates.fake(),
                null
            )
        );
    }

    //addTerminalContext................................................................................................

    @Test
    public void testAddTerminalContextWithInvalidUserFails() {
        final IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> this.createContext()
                .addTerminalContext(
                    (TerminalId terminalId) ->
                        new FakeTerminalContext() {
                            @Override
                            public TerminalId terminalId() {
                                return null;
                            }

                            @Override
                            public Optional<EmailAddress> user() {
                                return DIFFERENT_USER;
                            }
                        }
                )
        );

        this.checkEquals(
            "Added TerminalContext belongs to different user: different@example.com",
            thrown.getMessage()
        );
    }

    @Test
    public void testAddTerminalContext() {
        final TerminalContext added = this.createContext()
            .addTerminalContext(
                (TerminalId terminalId) -> TERMINAL_CONTEXT
            );

        assertSame(
            TERMINAL_CONTEXT,
            added
        );
    }

    // terminalContext..................................................................................................

    @Test
    public void testTerminalContextWithUnknownTerminalId() {
        this.checkEquals(
            Optional.empty(),
            this.createContext()
                .terminalContext(
                    TerminalId.with(404)
                )
        );
    }

    @Test
    public void testTerminalContextWithDifferentUser() {
        this.checkEquals(
            Optional.empty(),
            this.createContext()
                .terminalContext(DIFFERENT_TERMINAL_ID)
        );
    }

    @Test
    public void testTerminalContext() {
        this.checkEquals(
            Optional.of(TERMINAL_CONTEXT),
            this.createContext()
                .terminalContext(TERMINAL_ID)
        );
    }

    // removeTerminalContext............................................................................................

    @Test
    public void testRemoveTerminalContextWithUnknownTerminalId() {
        this.removeTerminalContextAndCheck(
            TerminalId.with(404),
            null
        );
    }

    @Test
    public void testRemoveTerminalContextWithDifferentUser() {
        this.removeTerminalContextAndCheck(
            DIFFERENT_TERMINAL_ID,
            null
        );
    }

    @Test
    public void testRemoveTerminalContext() {
        this.removeTerminalContextAndCheck(
            TERMINAL_ID,
            TERMINAL_ID
        );
    }

    private void removeTerminalContextAndCheck(final TerminalId terminalId,
                                               final TerminalId expected) {
        this.createContext()
            .removeTerminalContext(terminalId);
        this.checkEquals(
            expected,
            this.removed
        );
    }

    private final static String TO_STRING = "ToString123";

    @Override
    public UserFilteredTerminalServerContext createContext() {
        this.removed = null;

        return UserFilteredTerminalServerContext.with(
            (u) -> u.equals(USER),
            new FakeTerminalServerContext() {

                @Override
                public TerminalContext addTerminalContext(Function<TerminalId, TerminalContext> terminalContextFactory) {
                    Objects.requireNonNull(terminalContextFactory, "terminalContextFactory");

                    return terminalContextFactory.apply(TERMINAL_ID);
                }

                @Override
                public Optional<TerminalContext> terminalContext(final TerminalId id) {
                    Objects.requireNonNull(id, "id");

                    return Optional.ofNullable(
                        TERMINAL_ID.equals(id) ?
                            TERMINAL_CONTEXT :
                            DIFFERENT_TERMINAL_ID.equals(id) ?
                                DIFFERENT_TERMINAL_CONTEXT :
                                null
                    );
                }

                @Override
                public TerminalServerContext removeTerminalContext(final TerminalId id) {
                    Objects.requireNonNull(id, "id");

                    UserFilteredTerminalServerContextTest.this.removed = id;
                    return this;
                }

                @Override
                public String toString() {
                    return TO_STRING;
                }
            }
        );
    }

    private TerminalId removed;

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createContext(),
            TO_STRING
        );
    }

    // class............................................................................................................

    @Override
    public Class<UserFilteredTerminalServerContext> type() {
        return UserFilteredTerminalServerContext.class;
    }
}
