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
 * Reads a line from the {@link TerminalContext#readLine(long)}.
 */
final class TerminalExpressionFunctionReadLine<C extends TerminalExpressionEvaluationContext> extends TerminalExpressionFunction<String, C> {

    /**
     * Type safe instance getter.
     */
    static <C extends TerminalExpressionEvaluationContext> TerminalExpressionFunctionReadLine<C> instance() {
        return Cast.to(INSTANCE);
    }

    private final static TerminalExpressionFunctionReadLine INSTANCE = new TerminalExpressionFunctionReadLine<>();

    private TerminalExpressionFunctionReadLine() {
        super("readLine");
    }

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return PARAMETERS;
    }

    final static ExpressionFunctionParameter<Long> TIMEOUT = ExpressionFunctionParameterName.with("timeout")
        .optional(Long.class)
        .setKinds(ExpressionFunctionParameterKind.CONVERT_EVALUATE);


    private final static List<ExpressionFunctionParameter<?>> PARAMETERS = Lists.of(TIMEOUT);

    @Override
    public Class<String> returnType() {
        return String.class;
    }

    @Override
    public String apply(final List<Object> parameters,
                        final C context) {
        this.checkParameterCount(parameters);

        return context.readLine(
            TIMEOUT.get(parameters, 0)
                .orElse(DEFAULT_TIMEOUT) // https://github.com/mP1/walkingkooka-tree/issues/947
        ).orElse(null);
    }

    // @VisibleForTesting
    final static Long DEFAULT_TIMEOUT = 0L;

    @Override
    public boolean isPure(final ExpressionPurityContext expressionPurityContext) {
        return false;
    }
}
