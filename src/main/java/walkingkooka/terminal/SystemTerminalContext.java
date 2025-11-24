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

import javaemul.internal.annotations.GwtIncompatible;
import walkingkooka.environment.HasUser;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.text.LineEnding;
import walkingkooka.util.OpenChecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Optional;

/**
 * A {@link TerminalContext} that reads and write to the System IN and OUT streams.
 */
@GwtIncompatible
final class SystemTerminalContext implements TerminalContext {

    /**
     * Factory that creates a new {@link SystemTerminalContext}.
     */
    static SystemTerminalContext with(final TerminalId terminalId,
                                      final HasUser hasUser) {
        return new SystemTerminalContext(
            Objects.requireNonNull(terminalId, "terminalId"),
            Objects.requireNonNull(hasUser, "hasUser")
        );
    }

    private SystemTerminalContext(final TerminalId terminalId,
                                  final HasUser hasUser) {
        this.terminalId = terminalId;

        this.hasUser = hasUser;

        this.lineReader = new BufferedReader(
            new InputStreamReader(
                System.in
            )
        );
        this.lineEnding = LineEnding.from(
            System.lineSeparator()
        );

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
        if (timeout < 0) {
            throw new IllegalArgumentException("Invalid timeout " + timeout + " < 0");
        }

        this.openChecker.check();

        String line;

        try {
            line = this.lineReader.readLine();
        } catch (final IOException ignore) {
            line = null;
        }

        return Optional.ofNullable(line);
    }

    private final BufferedReader lineReader;

    @Override
    public void print(final CharSequence charSequence) {
        this.openChecker.check();

        System.out.print(charSequence);
    }

    @Override
    public LineEnding lineEnding() {
        return this.lineEnding;
    }

    private final LineEnding lineEnding;

    @Override
    public void flush() {
        this.openChecker.check();

        System.out.flush();
    }

    @Override
    public TerminalContext exitTerminal() {
        this.openChecker.close();
        return this;
    }

    @Override
    public boolean isTerminalOpen() {
        return false == this.openChecker.isClosed();
    }

    private final OpenChecker<IllegalStateException> openChecker;

    // Object...........................................................................................................

    @Override
    public String toString() {
        return this.terminalId().toString();
    }
}
