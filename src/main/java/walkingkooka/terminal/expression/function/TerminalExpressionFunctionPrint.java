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

package walkingkooka.terminal.expression.function;

import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;
import walkingkooka.terminal.expression.TerminalExpressionEvaluationContext;
import walkingkooka.tree.expression.ExpressionPurityContext;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;

import java.util.List;

/**
 * Prints the given parameter after converting it to {@link String}.
 */
final class TerminalExpressionFunctionPrint<C extends TerminalExpressionEvaluationContext> extends TerminalExpressionFunction<Void, C> {

    /**
     * Type safe instance getter.
     */
    static <C extends TerminalExpressionEvaluationContext> TerminalExpressionFunctionPrint<C> instance() {
        return Cast.to(INSTANCE);
    }

    private final static TerminalExpressionFunctionPrint INSTANCE = new TerminalExpressionFunctionPrint<>();

    private TerminalExpressionFunctionPrint() {
        super("print");
    }

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return PARAMETERS;
    }

    private final static List<ExpressionFunctionParameter<?>> PARAMETERS = Lists.of(TEXT);

    @Override
    public Class<Void> returnType() {
        return Void.class;
    }

    @Override
    public Void apply(final List<Object> parameters,
                      final C context) {
        this.checkParameterCount(parameters);

        context.print(
            TEXT.getOrFail(parameters, 0)
        );

        return null;
    }

    @Override
    public boolean isPure(ExpressionPurityContext expressionPurityContext) {
        return false;
    }
}
