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

import walkingkooka.reflect.PublicStaticHelper;
import walkingkooka.terminal.expression.TerminalExpressionEvaluationContext;
import walkingkooka.tree.expression.function.ExpressionFunction;

/**
 * A collection of functions that require a {@link TerminalExpressionEvaluationContext}.
 */
public final class TerminalExpressionFunctions implements PublicStaticHelper {

    /**
     * {@see TerminalExpressionFunctionExit}
     */
    public static <C extends TerminalExpressionEvaluationContext> ExpressionFunction<Void, C> exit() {
        return TerminalExpressionFunctionExit.instance();
    }

    /**
     * {@see TerminalExpressionFunctionPrint}
     */
    public static <C extends TerminalExpressionEvaluationContext> ExpressionFunction<Void, C> print() {
        return TerminalExpressionFunctionPrint.instance();
    }

    /**
     * {@see TerminalExpressionFunctionPrintEnv}
     */
    public static <C extends TerminalExpressionEvaluationContext> ExpressionFunction<Void, C> printEnv() {
        return TerminalExpressionFunctionPrintEnv.instance();
    }

    /**
     * {@see TerminalExpressionFunctionPrintln}
     */
    public static <C extends TerminalExpressionEvaluationContext> ExpressionFunction<Void, C> println() {
        return TerminalExpressionFunctionPrintln.instance();
    }

    /**
     * {@see TerminalExpressionFunctionReadLine}
     */
    public static <C extends TerminalExpressionEvaluationContext> ExpressionFunction<String, C> readLine() {
        return TerminalExpressionFunctionReadLine.instance();
    }

    /**
     * {@see TerminalExpressionFunctionShell}
     */
    public static <C extends TerminalExpressionEvaluationContext> ExpressionFunction<Integer, C> shell() {
        return TerminalExpressionFunctionShell.instance();
    }

    /**
     * Stop creation
     */
    private TerminalExpressionFunctions() {
        throw new UnsupportedOperationException();
    }
}
