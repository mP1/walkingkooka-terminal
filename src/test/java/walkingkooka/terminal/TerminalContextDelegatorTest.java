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

package walkingkooka.terminal;

import org.junit.jupiter.api.Test;
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.environment.EnvironmentContexts;
import walkingkooka.io.TextReaders;
import walkingkooka.terminal.TerminalContextDelegatorTest.TestTerminalContextDelegator;
import walkingkooka.text.LineEnding;
import walkingkooka.text.printer.Printers;
import walkingkooka.text.printer.TreePrintableTesting;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;

public final class TerminalContextDelegatorTest implements TerminalContextTesting<TestTerminalContextDelegator> {

    private final static TerminalId TERMINAL_ID = TerminalId.parse("1");

    @Test
    public void testTerminalId() {
        this.terminalIdAndCheck(
            new TestTerminalContextDelegator(),
            TERMINAL_ID
        );
    }

    @Override
    public TestTerminalContextDelegator createContext() {
        return new TestTerminalContextDelegator();
    }

    @Override
    public Class<TestTerminalContextDelegator> type() {
        return TestTerminalContextDelegator.class;
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }

    final static class TestTerminalContextDelegator implements TerminalContextDelegator {

        @Override
        public Object evaluate(final String expression) {
            Objects.requireNonNull(expression, "expression");
            throw new UnsupportedOperationException();
        }

        @Override
        public TerminalContext terminalContext() {
            return terminalContext;
        }

        private final TerminalContext terminalContext = TerminalContexts.basic(
            TerminalContextDelegatorTest.TERMINAL_ID,
            () -> true, // openTester
            TextReaders.fake(), // input
            Printers.fake(), // output
            Printers.fake(), // error
            (e, c) -> {
                throw new UnsupportedOperationException();
            },
            (e) -> {
                throw new UnsupportedOperationException();
            },
            EnvironmentContexts.map(
                EnvironmentContexts.empty(
                    TreePrintableTesting.INDENTATION,
                    LineEnding.NL,
                    Locale.ENGLISH,
                    () -> LocalDateTime.MIN,
                    EnvironmentContext.ANONYMOUS
                )
            )
        );

        @Override
        public TerminalContext cloneEnvironment() {
            return new TestTerminalContextDelegator();
        }

        @Override
        public TerminalContext setEnvironmentContext(final EnvironmentContext context) {
            Objects.requireNonNull(context, "context");
            return new TestTerminalContextDelegator();
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }
}
