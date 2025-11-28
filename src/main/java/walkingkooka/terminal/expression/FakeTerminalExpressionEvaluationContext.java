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

package walkingkooka.terminal.expression;

import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.io.TextReader;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.terminal.TerminalId;
import walkingkooka.text.printer.Printer;
import walkingkooka.tree.expression.FakeExpressionEvaluationContext;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;

public class FakeTerminalExpressionEvaluationContext extends FakeExpressionEvaluationContext implements TerminalExpressionEvaluationContext {

    public FakeTerminalExpressionEvaluationContext() {
        super();
    }

    @Override
    public TerminalId terminalId() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isTerminalInteractive() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isTerminalOpen() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TerminalExpressionEvaluationContext exitTerminal() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TextReader input() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Printer output() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Printer error() {
        throw new UnsupportedOperationException();
    }

    // EnvironmentContext...............................................................................................

    @Override
    public TerminalExpressionEvaluationContext cloneEnvironment() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> Optional<T> environmentValue(final EnvironmentValueName<T> name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<EnvironmentValueName<?>> environmentValueNames() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> TerminalExpressionEvaluationContext setEnvironmentValue(final EnvironmentValueName<T> name,
                                                                       final T value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TerminalExpressionEvaluationContext removeEnvironmentValue(final EnvironmentValueName<?> name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<EmailAddress> user() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TerminalExpressionEvaluationContext setUser(final Optional<EmailAddress> user) {
        throw new UnsupportedOperationException();
    }

    // LocaleContext....................................................................................................

    @Override
    public TerminalExpressionEvaluationContext setLocale(final Locale locale) {
        throw new UnsupportedOperationException();
    }
}
