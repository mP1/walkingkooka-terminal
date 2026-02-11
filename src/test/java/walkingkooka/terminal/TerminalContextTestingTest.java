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

import walkingkooka.environment.EnvironmentContext;
import walkingkooka.environment.EnvironmentContexts;
import walkingkooka.io.TextReader;
import walkingkooka.io.TextReaders;
import walkingkooka.terminal.TerminalContextTestingTest.TestTerminalContext;
import walkingkooka.text.Indentation;
import walkingkooka.text.LineEnding;
import walkingkooka.text.printer.Printer;
import walkingkooka.text.printer.Printers;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;

public final class TerminalContextTestingTest implements TerminalContextTesting<TestTerminalContext> {

    @Override
    public TestTerminalContext createContext() {
        return new TestTerminalContext();
    }

    // class............................................................................................................

    @Override
    public Class<TestTerminalContext> type() {
        return TestTerminalContext.class;
    }

    static class TestTerminalContext implements TerminalContextDelegator {

        @Override
        public TextReader input() {
            return TextReaders.fake();
        }

        @Override
        public Printer output() {
            return Printers.fake();
        }

        @Override
        public Printer error() {
            return Printers.fake();
        }

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
            TerminalId.with(1),
            () -> true, // openTester
            TextReaders.fake(), // input
            Printers.fake(), // output
            Printers.fake(), // error
            (e, c) -> {
                throw new UnsupportedOperationException();
            },
            (ev) -> {
                throw new UnsupportedOperationException();
            },
            EnvironmentContexts.map(
                EnvironmentContexts.empty(
                    Indentation.SPACES2,
                    LineEnding.NL,
                    Locale.ENGLISH,
                    () -> LocalDateTime.MIN,
                    EnvironmentContext.ANONYMOUS
                )
            )
        );

        @Override
        public TerminalContext cloneEnvironment() {
            return new TestTerminalContext();
        }

        @Override
        public TerminalContext setEnvironmentContext(final EnvironmentContext context) {
            Objects.requireNonNull(context, "context");
            return new TestTerminalContext();
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }
}
