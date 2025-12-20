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
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.io.TextReader;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.text.LineEnding;
import walkingkooka.text.printer.Printer;

import java.util.Locale;
import java.util.Optional;

/**
 * A {@link Context} that provides some line-based interactivity, to read lines and print text.
 */
public interface TerminalContext extends EnvironmentContext {

    /**
     * Returns the {@link TerminalId} identifying this session.
     */
    TerminalId terminalId();

    /**
     * Used to programmatically quite or close this terminal session.
     */
    TerminalContext exitTerminal();

    /**
     * Tests if the terminal has been closed.
     */
    boolean isTerminalOpen();

    /**
     * Returns a {@link TextReader} that may be used to read any text input
     */
    TextReader input();

    /**
     * A {@link Printer} for output.
     */
    Printer output();

    /**
     * A {@link Printer} for errors.
     */
    Printer error();

    /**
     * Evaluate the given expression into a value.
     */
    Object evaluate(String expression);

    // EnvironmentContext...............................................................................................

    @Override
    TerminalContext cloneEnvironment();

    @Override
    TerminalContext setEnvironmentContext(final EnvironmentContext environmentContext);

    @Override
    <T> TerminalContext setEnvironmentValue(final EnvironmentValueName<T> name,
                                            final T value);

    @Override
    TerminalContext removeEnvironmentValue(final EnvironmentValueName<?> name);

    @Override
    TerminalContext setLineEnding(final LineEnding lineEnding);

    @Override
    TerminalContext setLocale(final Locale locale);

    @Override
    TerminalContext setUser(final Optional<EmailAddress> user);
}
