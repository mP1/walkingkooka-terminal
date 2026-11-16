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

import walkingkooka.currency.CurrencyCode;
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.logging.LoggingContext;
import walkingkooka.terminal.TerminalContext;
import walkingkooka.terminal.TerminalContextDelegator;
import walkingkooka.text.Indentation;
import walkingkooka.text.LineEnding;
import walkingkooka.tree.expression.CanEvaluateString;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.ExpressionEvaluationContextDelegator;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Locale;

public interface TerminalExpressionEvaluationContextDelegator extends TerminalExpressionEvaluationContext,
    ExpressionEvaluationContextDelegator,
    TerminalContextDelegator {

    TerminalExpressionEvaluationContext terminalExpressionEvaluationContext();

    // CanEvaluateStringDelegator......................................................................................

    @Override
    default CanEvaluateString canEvaluateString() {
        return this.expressionEvaluationContext();
    }

    // ExpressionEvaluationContextDelegator.............................................................................

    @Override
    default ExpressionEvaluationContext expressionEvaluationContext() {
        return this.terminalExpressionEvaluationContext();
    }

    @Override
    default EnvironmentContext environmentContext() {
        return this.terminalExpressionEvaluationContext();
    }

    @Override
    default LoggingContext loggingContext() {
        return this.environmentContext();
    }

    @Override
    default Charset charset() {
        return this.environmentContext()
            .charset();
    }

    @Override
    default CurrencyCode currencyCode() {
        return this.environmentContext()
            .currencyCode();
    }

    @Override
    default Indentation indentation() {
        return this.environmentContext()
            .indentation();
    }

    @Override
    default LineEnding lineEnding() {
        return this.environmentContext()
            .lineEnding();
    }

    @Override
    default Locale locale() {
        return this.environmentContext()
            .locale();
    }

    @Override
    default void setLocale(final Locale locale) {
        this.environmentContext()
            .setLocale(locale);
    }

    @Override
    default LocalDateTime now() {
        return this.environmentContext()
            .now();
    }

    // TerminalContextDelegator.........................................................................................

    @Override
    default TerminalContext terminalContext() {
        return this.terminalExpressionEvaluationContext();
    }
}
