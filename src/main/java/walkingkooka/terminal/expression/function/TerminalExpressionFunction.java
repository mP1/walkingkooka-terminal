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

import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.terminal.expression.TerminalExpressionEvaluationContext;
import walkingkooka.tree.expression.ExpressionFunctionName;
import walkingkooka.tree.expression.function.ExpressionFunction;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterKind;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterName;

import java.util.Optional;

abstract class TerminalExpressionFunction<T, C extends TerminalExpressionEvaluationContext> implements ExpressionFunction<T, C> {

    TerminalExpressionFunction(final String name) {
        this.name = Optional.of(
            ExpressionFunctionName.with(name)
        );
    }

    @Override
    public final Optional<ExpressionFunctionName> name() {
        return this.name;
    }

    private final Optional<ExpressionFunctionName> name;

    final static ExpressionFunctionParameter<String> TEXT = ExpressionFunctionParameterName.with("text")
        .required(String.class)
        .setKinds(ExpressionFunctionParameterKind.CONVERT_EVALUATE);

    final static ExpressionFunctionParameter<EnvironmentValueName<?>> ENVIRONMENT_VALUE_NAME = ExpressionFunctionParameterName.with("environmentValue")
        .variable(EnvironmentValueName.CLASS_WILDCARD)
        .setKinds(ExpressionFunctionParameterKind.CONVERT_EVALUATE);

    @Override
    public final String toString() {
        return this.name.get()
            .toString();
    }
}
