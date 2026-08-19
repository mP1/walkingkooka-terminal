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

import org.junit.jupiter.api.Test;
import walkingkooka.environment.EnvironmentContextTesting2;
import walkingkooka.terminal.TerminalContextTesting2;
import walkingkooka.tree.expression.ExpressionEvaluationContextTesting2;

public interface TerminalExpressionEvaluationContextTesting2<C extends TerminalExpressionEvaluationContext> extends TerminalExpressionEvaluationContextTesting,
    ExpressionEvaluationContextTesting2<C>,
    TerminalContextTesting2<C>,
    EnvironmentContextTesting2<C> {

    @Test
    @Override
    default void testSetLocaleWithNullFails() {
        ExpressionEvaluationContextTesting2.super.testSetLocaleWithNullFails();
    }

    // evaluate.........................................................................................................

    @Test
    @Override
    default void testEvaluateWithNullExpressionFails() {
        ExpressionEvaluationContextTesting2.super.testEvaluateWithNullFails();
    }

    @Override
    default void evaluateAndCheck(final String expression,
                                  final Object expected) {
        ExpressionEvaluationContextTesting2.super.evaluateAndCheck(
            this.createContext(),
            expression,
            expected
        );
    }

    // class............................................................................................................

    @Override//
    default String typeNamePrefix() {
        return "";
    }

    @Override//
    default String typeNameSuffix() {
        return TerminalExpressionEvaluationContext.class.getSimpleName();
    }
}
