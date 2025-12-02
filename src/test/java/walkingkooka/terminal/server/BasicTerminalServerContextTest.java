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
import walkingkooka.Cast;
import walkingkooka.terminal.FakeTerminalContext;
import walkingkooka.terminal.TerminalContext;
import walkingkooka.terminal.TerminalId;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class BasicTerminalServerContextTest implements TerminalServerContextTesting<BasicTerminalServerContext> {

    @Test
    public void testWithNullNextTerminalIdFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicTerminalServerContext.with(
                null
            )
        );
    }

    // addTerminalContext...............................................................................................

    @Test
    public void testAddTerminalContextWithDifferentTerminalIdFails() {
        final BasicTerminalServerContext context = BasicTerminalServerContext.with(
            () -> TerminalId.with(1)
        );

        final IllegalStateException thrown = assertThrows(
            IllegalStateException.class,
            () -> context.addTerminalContext(
                (i) -> new TestTerminalContext(
                    TerminalId.with(999)
                )
            )
        );

        this.checkEquals(
            "TerminalContext:TerminalId different 3e7 from given 1",
            thrown.getMessage()
        );
    }

    @Test
    public void testAddTerminalContextWithDuplicateTerminalIdFails() {
        final TerminalId terminalId = TerminalId.with(999);

        final BasicTerminalServerContext context = BasicTerminalServerContext.with(
            () -> terminalId
        );

        context.addTerminalContext(
            (i) -> new TestTerminalContext(i)
        );

        final IllegalStateException thrown = assertThrows(
            IllegalStateException.class,
            () -> context.addTerminalContext(
                (i) -> new TestTerminalContext(i)
            )
        );

        this.checkEquals(
            "TerminalContext created with duplicate TerminalId: " + terminalId,
            thrown.getMessage()
        );
    }

    @Test
    public void testAddTerminalContext() {
        final TerminalId terminalId = TerminalId.with(999);

        final BasicTerminalServerContext context = BasicTerminalServerContext.with(
            () -> terminalId
        );

        final TerminalContext terminalContext = context.addTerminalContext(
            (i) -> new TestTerminalContext(i)
        );
        this.checkEquals(
            terminalId,
            terminalContext.terminalId(),
            "terminalId"
        );
    }

    @Override
    public BasicTerminalServerContext createContext() {
        return BasicTerminalServerContext.with(
            () -> {
                throw new UnsupportedOperationException();
            }
        );
    }

    final static class TestTerminalContext extends FakeTerminalContext {

        TestTerminalContext(final TerminalId terminalId) {
            this.terminalId = terminalId;
        }

        @Override
        public TerminalId terminalId() {
            return this.terminalId;
        }

        private TerminalId terminalId;
    }

    // class............................................................................................................

    @Override
    public Class<BasicTerminalServerContext> type() {
        return Cast.to(BasicTerminalServerContext.class);
    }
}
