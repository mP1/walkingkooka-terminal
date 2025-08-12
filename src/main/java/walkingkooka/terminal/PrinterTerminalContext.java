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

/**
 * A {@link TerminalContext} that prints to the the given {@link Printer} but attempts to read a line will always
 * immediately fail.
 */
final class PrinterTerminalContext implements TerminalContext,
    PrinterDelegator {

    static PrinterTerminalContext with(final Printer printer) {
        return new PrinterTerminalContext(
            Objects.requireNonNull(printer, "printer")
        );
    }

    private PrinterTerminalContext(final Printer printer) {
        this.printer = printer;
    }

    @Override
    public boolean isTerminalInteractive() {
        return false;
    }

    @Override
    public Optional<String> readLine(final long timeout) {
        if (timeout < 0) {
            throw new IllegalArgumentException("Invalid timeout " + timeout + " < 0");
        }
        return Optional.empty();
    }

    // PrinterDelegator.................................................................................................

    @Override
    public Printer printer() {
        return this.printer;
    }

    private final Printer printer;

    // Object...........................................................................................................

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " printer: " + this.printer;
    }
}
