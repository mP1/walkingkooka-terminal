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

import walkingkooka.text.printer.Printer;
import walkingkooka.text.printer.PrinterDelegator;
import walkingkooka.util.OpenChecker;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * A {@link TerminalContext} that reads line from a {@link Function}, with the timeout, and prints to a {@link Printer}.
 */
final class BasicTerminalContext implements TerminalContext,
    PrinterDelegator {

    static BasicTerminalContext with(final TerminalId terminalId,
                                     final Function<Long, Optional<String>> lineReader,
                                     final Printer printer) {
        return new BasicTerminalContext(
            Objects.requireNonNull(terminalId, "terminalId"),
            Objects.requireNonNull(lineReader, "lineReader"),
            Objects.requireNonNull(printer, "printer")
        );
    }

    private BasicTerminalContext(final TerminalId terminalId,
                                 Function<Long, Optional<String>> lineReader,
                                 final Printer printer) {
        this.terminalId = terminalId;
        this.lineReader = lineReader;
        this.printer = printer;

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
    public TerminalContext quitTerminal() {
        this.openChecker.close();
        return this;
    }

    private final OpenChecker<IllegalStateException> openChecker;

    // PrinterDelegator.................................................................................................

    @Override
    public Printer printer() {
        this.openChecker.check();
        return this.printer;
    }

    private final Printer printer;

    // Object...........................................................................................................

    @Override
    public String toString() {
        return this.terminalId + " lineReader: " + this.lineReader + " printer: " + this.printer;
    }
}
