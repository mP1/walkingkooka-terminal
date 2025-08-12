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

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * A {@link TerminalContext} that reads line from a {@link Function}, with the timeout, and prints to a {@link Printer}.
 */
final class BasicTerminalContext implements TerminalContext,
    PrinterDelegator {

    static BasicTerminalContext with(final Function<Long, Optional<String>> lineReader,
                                     final Printer printer) {
        return new BasicTerminalContext(
            Objects.requireNonNull(lineReader, "lineReader"),
            Objects.requireNonNull(printer, "printer")
        );
    }

    private BasicTerminalContext(final Function<Long, Optional<String>> lineReader,
                                 final Printer printer) {
        this.lineReader = lineReader;
        this.printer = printer;
    }

    @Override
    public boolean isTerminalInteractive() {
        return true;
    }

    @Override
    public Optional<String> readLine(final long timeout) {
        return this.lineReader.apply(timeout);
    }

    private final Function<Long, Optional<String>> lineReader;

    // PrinterDelegator.................................................................................................

    @Override
    public Printer printer() {
        return this.printer;
    }

    private final Printer printer;

    // Object...........................................................................................................

    @Override
    public String toString() {
        return "lineReader: " + this.lineReader + " printer: " + this.printer;
    }
}
