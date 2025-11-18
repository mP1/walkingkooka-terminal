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
import walkingkooka.environment.HasUser;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.text.printer.Printers;

import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class BasicTerminalContextTest implements TerminalContextTesting<BasicTerminalContext>,
    ToStringTesting<BasicTerminalContext> {

    private final static TerminalId TERMINAL_ID = TerminalId.parse("123");

    private final static HasUser HAS_USER = () -> Optional.of(
        EmailAddress.parse("user@example.com")
    );

    private final static Function<Long, Optional<String>> LINE_READER = (timeout) -> {
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout");
        }
        throw new UnsupportedOperationException();
    };

    @Test
    public void testWithNullTerminalIdFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicTerminalContext.with(
                null,
                HAS_USER,
                LINE_READER,
                Printers.fake()
            )
        );
    }

    @Test
    public void testWithNullHasUserFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicTerminalContext.with(
                TERMINAL_ID,
                null,
                LINE_READER,
                Printers.fake()
            )
        );
    }

    @Test
    public void testWithNullLineReaderFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicTerminalContext.with(
                TERMINAL_ID,
                HAS_USER,
                null,
                Printers.fake()
            )
        );
    }

    @Test
    public void testWithNullPrinterFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicTerminalContext.with(
                TERMINAL_ID,
                HAS_USER,
                LINE_READER,
                null
            )
        );
    }

    @Override
    public BasicTerminalContext createContext() {
        return BasicTerminalContext.with(
            TERMINAL_ID,
            HAS_USER,
            LINE_READER,
            Printers.sysOut()
        );
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createContext(),
            TERMINAL_ID + " lineReader: " + LINE_READER + " printer: " + Printers.sysOut()
        );
    }

    // class............................................................................................................

    @Override
    public Class<BasicTerminalContext> type() {
        return BasicTerminalContext.class;
    }
}
