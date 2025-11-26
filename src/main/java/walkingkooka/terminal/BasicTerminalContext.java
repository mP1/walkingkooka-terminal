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

import walkingkooka.environment.HasUser;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.text.printer.Printer;
import walkingkooka.util.OpenChecker;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * A {@link TerminalContext} that reads line from a {@link Function}, with the timeout, and prints to a {@link Printer}.
 */
final class BasicTerminalContext implements TerminalContext {

    static BasicTerminalContext with(final TerminalId terminalId,
                                     final HasUser hasUser,
                                     final Function<Long, Optional<String>> lineReader,
                                     final Printer output,
                                     final Printer error) {
        return new BasicTerminalContext(
            Objects.requireNonNull(terminalId, "terminalId"),
            Objects.requireNonNull(hasUser, "hasUser"),
            Objects.requireNonNull(lineReader, "lineReader"),
            Objects.requireNonNull(output, "output"),
            Objects.requireNonNull(error, "error")
        );
    }

    private BasicTerminalContext(final TerminalId terminalId,
                                 final HasUser hasUser,
                                 final Function<Long, Optional<String>> lineReader,
                                 final Printer output,
                                 final Printer error) {
        this.terminalId = terminalId;
        this.hasUser = hasUser;
        this.lineReader = lineReader;
        this.output = output;
        this.error = error;

        this.openChecker = OpenChecker.with(
            "Terminal closed",
            (String message) -> new IllegalStateException(message)
        );
    }

    @Override
    public TerminalId terminalId() {
        return this.terminalId;
    }

    private final TerminalId terminalId;

    @Override
    public Optional<EmailAddress> user() {
        return this.hasUser.user();
    }

    private final HasUser hasUser;

    @Override
    public boolean isTerminalInteractive() {
        return true;
    }

    @Override
    public Optional<String> readLine(final long timeout) {
        this.openChecker.check();

        return this.lineReader.apply(timeout);
    }

    private final Function<Long, Optional<String>> lineReader;

    @Override
    public TerminalContext exitTerminal() {
        this.openChecker.close();
        return this;
    }

    @Override
    public boolean isTerminalOpen() {
        return false == this.openChecker.isClosed();
    }

    @Override
    public Printer output() {
        return this.output;
    }

    private final Printer output;

    @Override
    public Printer error() {
        return this.error;
    }

    private final Printer error;

    private final OpenChecker<IllegalStateException> openChecker;

    // Object...........................................................................................................

    @Override
    public String toString() {
        return this.terminalId + ", lineReader: " + this.lineReader + ", output: " + this.output + ", error: " + this.error;
    }
}
