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

import walkingkooka.environment.EnvironmentContext;
import walkingkooka.terminal.TerminalContext;
import walkingkooka.terminal.TerminalContextDelegator;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.ExpressionEvaluationContextDelegator;

public interface TerminalExpressionEvaluationContextDelegator extends TerminalExpressionEvaluationContext,
    ExpressionEvaluationContextDelegator,
    TerminalContextDelegator {

    @Override
    Object evaluate(final String expression);

    TerminalExpressionEvaluationContext terminalExpressionEvaluationContext();

    // ExpressionEvaluationContextDelegator.............................................................................

    @Override
    default ExpressionEvaluationContext expressionEvaluationContext() {
        return this.terminalExpressionEvaluationContext();
    }

    @Override
    default EnvironmentContext environmentContext() {
        return this.terminalExpressionEvaluationContext();
    }

    // TerminalContextDelegator.........................................................................................

    @Override
    default TerminalContext terminalContext() {
        return this.terminalExpressionEvaluationContext();
    }
}
