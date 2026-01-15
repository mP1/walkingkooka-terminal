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
import walkingkooka.environment.EnvironmentContextDelegator;
import walkingkooka.io.TextReader;
import walkingkooka.text.printer.Printer;

public interface TerminalContextDelegator extends TerminalContext,
    EnvironmentContextDelegator {

    @Override
    default TerminalId terminalId() {
        return this.terminalContext()
            .terminalId();
    }

    @Override
    default boolean isTerminalOpen() {
        return this.terminalContext()
            .isTerminalOpen();
    }

    @Override
    default TerminalContext exitTerminal() {
        return this.terminalContext()
            .exitTerminal();
    }

    @Override
    default TextReader input() {
        return this.terminalContext()
            .input();
    }

    @Override
    default Printer output() {
        return this.terminalContext()
            .output();
    }

    @Override
    default Printer error() {
        return this.terminalContext()
            .error();
    }

    TerminalContext terminalContext();

    // EnvironmentContextDelegator......................................................................................

    @Override
    default EnvironmentContext environmentContext() {
        return this.terminalContext();
    }
}
