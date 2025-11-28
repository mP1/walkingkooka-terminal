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

import walkingkooka.text.CharSequences;

import java.util.Objects;

/**
 * A {@link TerminalShell} that reads input, buffering and supporting line-continuation using the backslash as well
 * as un-escaping backslash escape sequences.
 */
final class BasicTerminalShell implements TerminalShell {

    private final static String LINE_CONTINUATION = "\\";

    static BasicTerminalShell with(final long timeout) {
        if (timeout <= 0L) {
            throw new IllegalArgumentException("Invalid Timeout " + timeout + " <= 0");
        }

        return new BasicTerminalShell(timeout);
    }

    private BasicTerminalShell(final long timeout) {
        this.timeout = timeout;
    }

    @Override
    public void start(final TerminalShellContext context) {
        Objects.requireNonNull(context, "context");

        StringBuilder buffer = new StringBuilder();

        while (context.isTerminalOpen()) {
            final String line = context.input()
                .readLine(this.timeout)
                .orElse(null);

            if (null != line) {
                final int bufferLength = buffer.length();

                int first = 0;

                if (bufferLength > 0) {
                    buffer.setLength(
                        buffer.length() - 1
                    );
                    first = 1;
                }

                final int lineLength = line.length();
                buffer.append(
                    line,
                    Math.min(
                        first,
                        lineLength
                    ),
                    lineLength
                );

                if (false == line.endsWith(LINE_CONTINUATION)) {
                    context.evaluate(
                        CharSequences.unescape(
                            buffer.toString()
                        ).toString()
                    );

                    buffer = new StringBuilder();
                }
            }
        }
    }

    private final long timeout;

    // Object...........................................................................................................

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " timeout=" + this.timeout;
    }
}
