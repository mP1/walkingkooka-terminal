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

import walkingkooka.io.TextReader;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.text.printer.Printer;

import java.util.Optional;

public class FakeTerminalContext implements TerminalContext {

    public FakeTerminalContext() {
        super();
    }

    @Override
    public TerminalId terminalId() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TerminalContext exitTerminal() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isTerminalOpen() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TextReader input() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Printer output() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Printer error() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<EmailAddress> user() {
        throw new UnsupportedOperationException();
    }
}
