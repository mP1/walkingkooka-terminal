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

package walkingkooka.terminal.shell;

import walkingkooka.terminal.shell.TerminalShellContextTestingTest.TestTerminalShellContext;
import walkingkooka.text.printer.Printer;
import walkingkooka.text.printer.Printers;

import java.util.Objects;

public final class TerminalShellContextTestingTest implements TerminalShellContextTesting<TestTerminalShellContext> {

    @Override
    public void testReadLineWithNegativeTimeoutFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TestTerminalShellContext createContext() {
        return new TestTerminalShellContext();
    }

    @Override
    public Class<TestTerminalShellContext> type() {
        return TestTerminalShellContext.class;
    }

    final static class TestTerminalShellContext extends FakeTerminalShellContext {

        @Override
        public void evaluate(final String command) {
            Objects.requireNonNull(command, "command");
            throw new UnsupportedOperationException();
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
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }
}
