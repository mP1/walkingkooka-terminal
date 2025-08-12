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
import walkingkooka.ToStringTesting;
import walkingkooka.text.printer.Printers;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class PrinterTerminalContextTest implements TerminalContextTesting<PrinterTerminalContext>,
    ToStringTesting<PrinterTerminalContext> {

    @Test
    public void testWithNullPrinterFails() {
        assertThrows(
            NullPointerException.class,
            () -> PrinterTerminalContext.with(null)
        );
    }

    @Override
    public void testReadLineWithNegativeTimeoutFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public PrinterTerminalContext createContext() {
        return PrinterTerminalContext.with(
            Printers.sysOut()
        );
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createContext(),
            "PrinterTerminalContext printer: " + Printers.sysOut()
        );
    }

    // class............................................................................................................

    @Override
    public Class<PrinterTerminalContext> type() {
        return PrinterTerminalContext.class;
    }
}
