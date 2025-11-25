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

package walkingkooka.terminal.shell;

import org.junit.jupiter.api.Test;
import walkingkooka.ToStringTesting;
import walkingkooka.collect.list.Lists;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class BasicTerminalShellTest implements TerminalShellTesting<BasicTerminalShell>,
    ToStringTesting<BasicTerminalShell> {

    @Test
    public void testWithInvalidZeroTimeoutFails() {
        assertThrows(
            IllegalArgumentException.class,
            () -> BasicTerminalShell.with(0)
        );
    }

    @Test
    public void testWithInvalidNegativeTimeoutFails() {
        assertThrows(
            IllegalArgumentException.class,
            () -> BasicTerminalShell.with(-1)
        );
    }

    // start............................................................................................................

    @Test
    public void testStartMultipleLines() {
        final List<String> lines = Lists.of(
            "111",
            "2222",
            "",
            "4"
        );
        this.startAndCheck(
            lines,
            lines
        );
    }

    @Test
    public void testStartBrokenLine() {
        this.startAndCheck(
            Lists.of(
                "123\\",
                "\n456"
            ),
            Lists.of("123456")
        );
    }

    @Test
    public void testStartBrokenLine2() {
        this.startAndCheck(
            Lists.of(
                "123\\",
                "\n456\\",
                "\r789"
            ),
            Lists.of("123456789")
        );
    }

    @Test
    public void testStartEscapedCharacters() {
        this.startAndCheck(
            Lists.of("111\\t\\r\\n222"),
            Lists.of("111\t\r\n222")
        );
    }

    private void startAndCheck(final List<String> input,
                               final List<String> expected) {
        final Iterator<String> read = input.iterator();
        final List<String> printed = Lists.array();

        this.createShell()
            .start(
                new FakeTerminalShellContext() {

                    @Override
                    public boolean isTerminalOpen() {
                        return read.hasNext();
                    }

                    @Override
                    public Optional<String> readLine(final long timeout) {
                        return Optional.ofNullable(
                            read.hasNext() ?
                                read.next() :
                                null
                        );
                    }

                    @Override
                    public void executeTerminalCommand(final String command) {
                        printed.add(command);
                    }
                }
            );

        this.checkEquals(
            expected,
            printed
        );
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createShell(),
            "BasicTerminalShell timeout=100"
        );
    }

    // TerminalShell....................................................................................................

    @Override
    public BasicTerminalShell createShell() {
        return BasicTerminalShell.with(100);
    }

    // class............................................................................................................

    @Override
    public Class<BasicTerminalShell> type() {
        return BasicTerminalShell.class;
    }
}
