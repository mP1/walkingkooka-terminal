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
import walkingkooka.terminal.TerminalContextTestingTest.TestTerminalContext;

public final class TerminalContextTestingTest implements TerminalContextTesting<TestTerminalContext> {

    @Override
    public void testReadLineWithNegativeTimeoutFails() {
        throw new UnsupportedOperationException();
    }

    @Test
    public void testIsTerminalInteractive() {
        this.isTerminalInteractiveAndCheck(
            new TestTerminalContext(),
            true
        );
    }

    @Override
    public TestTerminalContext createContext() {
        return new TestTerminalContext();
    }

    // class............................................................................................................

    @Override
    public Class<TestTerminalContext> type() {
        return TestTerminalContext.class;
    }

    static class TestTerminalContext extends FakeTerminalContext {

        @Override
        public boolean isTerminalInteractive() {
            return true;
        }

        @Override
        public void close() {
            // nop
        }
    }
}
