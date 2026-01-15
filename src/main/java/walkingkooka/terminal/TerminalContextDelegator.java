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
import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.io.TextReader;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.text.LineEnding;
import walkingkooka.text.printer.Printer;

import java.util.Optional;

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
    default <T> TerminalContext setEnvironmentValue(final EnvironmentValueName<T> name,
                                                    final T value) {
        this.environmentContext()
            .setEnvironmentValue(
                name,
                value
            );
        return this;
    }

    @Override
    default TerminalContext removeEnvironmentValue(final EnvironmentValueName<?> name) {
        this.environmentContext()
            .removeEnvironmentValue(name);
        return this;
    }

    @Override
    default TerminalContext setLineEnding(final LineEnding lineEnding) {
        this.environmentContext()
            .setLineEnding(lineEnding);
        return this;
    }

    @Override
    default TerminalContext setUser(final Optional<EmailAddress> user) {
        this.environmentContext()
            .setUser(user);
        return this;
    }

    @Override
    default EnvironmentContext environmentContext() {
        return this.terminalContext();
    }
}
