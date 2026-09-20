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

package walkingkooka.terminal.logging;

import org.junit.jupiter.api.Test;
import walkingkooka.ToStringTesting;
import walkingkooka.logging.CanLogTesting2;
import walkingkooka.logging.LoggerPath;
import walkingkooka.logging.LoggingLevel;
import walkingkooka.terminal.FakeTerminalContext;
import walkingkooka.text.HasLineEndingTesting;
import walkingkooka.text.printer.Printer;
import walkingkooka.text.printer.Printers;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class TerminalCanLogTerminalContextTest implements CanLogTesting2<TerminalCanLogTerminalContext>,
    ToStringTesting<TerminalCanLogTerminalContext>,
    HasLineEndingTesting {

    @Test
    public void testWithNullTerminalContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> TerminalCanLogTerminalContext.with(null)
        );
    }

    @Test
    public void testLogError() {
        final StringBuilder b = new StringBuilder();

        final TerminalCanLogTerminalContext context = TerminalCanLogTerminalContext.with(
            new FakeTerminalContext() {

                @Override
                public Printer error() {
                    return this.error;
                }

                private final Printer error = Printers.stringBuilder(
                    b,
                    TerminalCanLogTerminalContextTest.LINE_ENDING
                );
            }
        );
        context.log(
            LoggingLevel.ERROR,
            "Message123",
            null
        );

        this.checkEquals(
            "ERROR Message123" + LINE_ENDING,
            b.toString()
        );
    }

    @Test
    public void testLogNonError() {
        final StringBuilder b = new StringBuilder();

        final TerminalCanLogTerminalContext context = TerminalCanLogTerminalContext.with(
            new FakeTerminalContext() {

                @Override
                public Printer output() {
                    return this.output;
                }

                private final Printer output = Printers.stringBuilder(
                    b,
                    TerminalCanLogTerminalContextTest.LINE_ENDING
                );
            }
        );
        context.log(
            LoggingLevel.DEBUG,
            "Message111",
            null
        );
        context.log(
            LoggingLevel.INFO,
            "Message222",
            null
        );
        context.log(
            LoggingLevel.WARN,
            "Message333",
            null
        );

        this.checkEquals(
            "DEBUG Message111\n" +
                "INFO Message222\n" +
                "WARN Message333\n",
            b.toString()
        );
    }

    @Override
    public TerminalCanLogTerminalContext createCanLog() {
        return TerminalCanLogTerminalContext.with(
            new FakeTerminalContext() {
                @Override
                public void logEnter(final LoggerPath logger) {
                    Objects.requireNonNull(logger, "logger");

                    throw new UnsupportedOperationException();
                }

                @Override
                public void logExit() {
                    // NOP
                }
            }
        );
    }

    // toString............................................................................................................

    @Test
    public void testToString() {
        final Printer output = Printers.fake();
        final Printer error = Printers.fake();

        this.toStringAndCheck(
            TerminalCanLogTerminalContext.with(
                new FakeTerminalContext() {
                    @Override
                    public Printer output() {
                        return output;
                    }

                    @Override
                    public Printer error() {
                        return error;
                    }
                }
            ),
            output + " " + error
        );
    }

    // class............................................................................................................

    @Override
    public Class<TerminalCanLogTerminalContext> type() {
        return TerminalCanLogTerminalContext.class;
    }
}
