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
import walkingkooka.Either;
import walkingkooka.collect.list.Lists;
import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.terminal.expression.TerminalExpressionEvaluationContext;
import walkingkooka.text.printer.Printer;
import walkingkooka.tree.expression.ExpressionPurityContext;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;

import java.util.Collection;
import java.util.List;

/**
 * Accepts a list of zero or more {@link EnvironmentValueName} and prints their values.
 * If no {@link EnvironmentValueName} is given each value will be prefixed with the name and an equals.
 * <pre>
 * printEnv user
 * > user@example.com
 *
 * printEnv
 * > lineEnding=\r\n
 * > locale=en-AU
 * > user=user@example.com
 * </pre>
 */
final class TerminalExpressionFunctionPrintEnv<C extends TerminalExpressionEvaluationContext> extends TerminalExpressionFunction<Void, C> {

    /**
     * Type safe instance getter.
     */
    static <C extends TerminalExpressionEvaluationContext> TerminalExpressionFunctionPrintEnv<C> instance() {
        return Cast.to(INSTANCE);
    }

    private final static TerminalExpressionFunctionPrintEnv INSTANCE = new TerminalExpressionFunctionPrintEnv<>();

    private TerminalExpressionFunctionPrintEnv() {
        super("printEnv");
    }

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return PARAMETERS;
    }

    private final static List<ExpressionFunctionParameter<?>> PARAMETERS = Lists.of(ENVIRONMENT_VALUE_NAME);

    @Override
    public Class<Void> returnType() {
        return Void.class;
    }

    @Override
    public Void apply(final List<Object> parameters,
                      final C context) {
        this.checkParameterCount(parameters);

        final boolean prefixWithName = parameters.isEmpty();

        final Collection<EnvironmentValueName<?>> names = prefixWithName ?
            context.environmentValueNames() :
            ENVIRONMENT_VALUE_NAME.getVariable(parameters, 0);

        final Printer printer = context.output();

        for (final EnvironmentValueName<?> name : names) {
            final Object value = context.environmentValue(name)
                .orElse(null);
            if (null != value) {
                final Either<String, String> stringValue = context.convert(
                    value,
                    String.class
                );

                if (stringValue.isLeft()) {
                    if (prefixWithName) {
                        printer.print(name.value());
                        printer.print("=");
                    }
                    printer.println(
                        stringValue.leftValue()
                    );
                }
            }
        }

        return null;
    }

    @Override
    public boolean isPure(final ExpressionPurityContext expressionPurityContext) {
        return false;
    }
}
