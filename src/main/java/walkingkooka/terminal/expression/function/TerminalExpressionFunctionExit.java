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
import walkingkooka.terminal.TerminalContext;
import walkingkooka.terminal.expression.TerminalExpressionEvaluationContext;
import walkingkooka.tree.expression.ExpressionPurityContext;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterKind;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterName;

import java.util.List;

/**
 * Quits the terminal session by invoking {@link TerminalContext#exitTerminal(Object)}
 */
final class TerminalExpressionFunctionExit<C extends TerminalExpressionEvaluationContext> extends TerminalExpressionFunction<Void, C> {

    /**
     * Type safe instance getter.
     */
    static <C extends TerminalExpressionEvaluationContext> TerminalExpressionFunctionExit<C> instance() {
        return Cast.to(INSTANCE);
    }

    private final static TerminalExpressionFunctionExit INSTANCE = new TerminalExpressionFunctionExit<>();

    private TerminalExpressionFunctionExit() {
        super("exit");
    }

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return PARAMETERS;
    }

    final static ExpressionFunctionParameter<Object> EXIT_VALUE = ExpressionFunctionParameterName.with("exitValue")
        .optional(Object.class)
        .setKinds(ExpressionFunctionParameterKind.EVALUATE_RESOLVE_REFERENCES);

    private final static List<ExpressionFunctionParameter<?>> PARAMETERS = Lists.of(
        EXIT_VALUE
    );

    @Override
    public Class<Void> returnType() {
        return Void.class;
    }

    @Override
    public Void apply(final List<Object> parameters,
                      final C context) {
        this.checkParameterCount(parameters);

        context.exitTerminal(
            EXIT_VALUE.get(
                parameters,
                0
            ).orElse(null)
        );
        return null;
    }

    @Override
    public boolean isPure(final ExpressionPurityContext expressionPurityContext) {
        return false;
    }
}
