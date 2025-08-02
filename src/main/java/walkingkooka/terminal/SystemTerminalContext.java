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
import walkingkooka.text.CharSequences;
import walkingkooka.text.LineEnding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A {@link TerminalContext} that reads and write to the System IN and OUT streams.
 */
@GwtIncompatible
final class SystemTerminalContext implements TerminalContext {

    /**
     * Singleton
     */
    final static SystemTerminalContext INSTANCE = new SystemTerminalContext();

    private SystemTerminalContext() {
        this.lineReader = new BufferedReader(
            new InputStreamReader(
                System.in
            )
        );
        this.lineEnding = LineEnding.from(
            System.lineSeparator()
        );
    }

    @Override
    public boolean isTerminalInteractive() {
        return true;
    }

    @Override
    public String readLine() {
        String line;

        try {
            line = this.lineReader.readLine();
        } catch (final IOException ignore) {
            line = "";
        }

        return CharSequences.nullToEmpty(line)
            .toString();
    }

    private final BufferedReader lineReader;

    @Override
    public void print(final CharSequence charSequence) {
        System.out.print(charSequence);
    }

    @Override
    public LineEnding lineEnding() {
        return this.lineEnding;
    }

    private final LineEnding lineEnding;

    @Override
    public void flush() {
        System.out.flush();
    }

    @Override
    public void close() {
        // NOP
    }

    // Object...........................................................................................................

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
