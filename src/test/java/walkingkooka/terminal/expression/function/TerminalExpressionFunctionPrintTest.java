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

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;
import walkingkooka.terminal.expression.FakeTerminalExpressionEvaluationContext;
import walkingkooka.terminal.expression.TerminalExpressionEvaluationContext;
import walkingkooka.terminal.expression.TerminalExpressionEvaluationContexts;
import walkingkooka.tree.expression.function.ExpressionFunctionTesting;

public final class TerminalExpressionFunctionPrintTest implements ExpressionFunctionTesting<TerminalExpressionFunctionPrint<TerminalExpressionEvaluationContext>, Void, TerminalExpressionEvaluationContext> {

    @Test
    public void testApply() {
        final StringBuilder b = new StringBuilder();

        final String text = "Abc123";

        final TerminalExpressionEvaluationContext context = new FakeTerminalExpressionEvaluationContext() {

            @Override
            public void print(final CharSequence text) {
                b.append(text);
            }
        };

        this.applyAndCheck(
            TerminalExpressionFunctionPrint.instance(),
            Lists.of(text),
            context,
            null
        );

        this.checkEquals(
            text,
            b.toString()
        );
    }

    @Test
    public void testIsPure() {
        this.isPureAndCheck(
            TerminalExpressionFunctionPrint.instance(),
            this.createContext(),
            false
        );
    }

    @Override
    public TerminalExpressionFunctionPrint<TerminalExpressionEvaluationContext> createBiFunction() {
        return TerminalExpressionFunctionPrint.instance();
    }

    @Override
    public TerminalExpressionEvaluationContext createContext() {
        return TerminalExpressionEvaluationContexts.fake();
    }

    @Override
    public int minimumParameterCount() {
        return 1;
    }

    @Override
    public Class<TerminalExpressionFunctionPrint<TerminalExpressionEvaluationContext>> type() {
        return Cast.to(TerminalExpressionFunctionPrint.class);
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }
}
