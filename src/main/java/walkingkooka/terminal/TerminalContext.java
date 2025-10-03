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

import walkingkooka.Context;
import walkingkooka.text.printer.Printer;

import java.util.Optional;

/**
 * A {@link Context} that provides some line-based interactivity, to read lines and print text.
 */
public interface TerminalContext extends Context, Printer {

    /**
     * Returns the {@link TerminalId} identifying this session.
     */
    TerminalId terminalId();

    /**
     * May be used to test if a terminal is interactive accepting input from a user.
     */
    boolean isTerminalInteractive();

    /**
     * Read a line of text from the current terminal.
     */
    Optional<String> readLine(long timeout);
}
