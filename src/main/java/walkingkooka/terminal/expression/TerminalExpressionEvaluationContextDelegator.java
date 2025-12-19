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
import walkingkooka.net.email.EmailAddress;
import walkingkooka.terminal.TerminalContext;
import walkingkooka.terminal.TerminalContextDelegator;
import walkingkooka.text.LineEnding;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.ExpressionEvaluationContextDelegator;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

public interface TerminalExpressionEvaluationContextDelegator extends TerminalExpressionEvaluationContext,
    ExpressionEvaluationContextDelegator,
    TerminalContextDelegator {

    @Override
    Object evaluate(final String expression);

    TerminalExpressionEvaluationContext terminalExpressionEvaluationContext();
    
    // ExpressionEvaluationContextDelegator.............................................................................

    @Override
    default <T> TerminalExpressionEvaluationContext setEnvironmentValue(final EnvironmentValueName<T> name,
                                                                        final T value) {
        this.terminalExpressionEvaluationContext()
            .setEnvironmentValue(
                name,
                value
            );
        return this;
    }

    @Override
    default TerminalExpressionEvaluationContext removeEnvironmentValue(final EnvironmentValueName<?> name) {
        this.terminalExpressionEvaluationContext()
            .removeEnvironmentValue(name);
        return this;
    }

    @Override
    default LocalDateTime now() {
        return this.terminalExpressionEvaluationContext()
            .now();
    }

    @Override
    default TerminalExpressionEvaluationContext setLineEnding(final LineEnding lineEnding) {
        this.terminalExpressionEvaluationContext()
            .setLineEnding(lineEnding);
        return this;
    }

    @Override
    default Locale locale() {
        return this.terminalExpressionEvaluationContext()
            .locale();
    }

    @Override
    default TerminalExpressionEvaluationContext setLocale(final Locale locale) {
        this.terminalExpressionEvaluationContext()
            .setLocale(locale);
        return this;
    }

    @Override
    default Optional<EmailAddress> user() {
        return this.terminalContext()
            .user();
    }

    @Override
    default TerminalExpressionEvaluationContext setUser(final Optional<EmailAddress> user) {
        this.terminalExpressionEvaluationContext()
            .setUser(user);
        return this;
    }

    @Override
    default ExpressionEvaluationContext expressionEvaluationContext() {
        return this.terminalExpressionEvaluationContext();
    }

    // TerminalContextDelegator.........................................................................................

    @Override
    default TerminalExpressionEvaluationContext exitTerminal() {
        return this.terminalExpressionEvaluationContext()
            .exitTerminal();
    }

    @Override
    default TerminalContext terminalContext() {
        return this.terminalExpressionEvaluationContext();
    }
}
