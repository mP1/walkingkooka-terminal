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

public final class TerminalExpressionFunctionExitTest implements ExpressionFunctionTesting<TerminalExpressionFunctionExit<TerminalExpressionEvaluationContext>, Void, TerminalExpressionEvaluationContext> {

    @Test
    public void testApply() {
        this.quit = false;

        final TerminalExpressionEvaluationContext context = new FakeTerminalExpressionEvaluationContext() {

            @Override
            public TerminalExpressionEvaluationContext quitTerminal() {
                TerminalExpressionFunctionExitTest.this.quit = true;
                return this;
            }
        };

        this.applyAndCheck(
            TerminalExpressionFunctionExit.instance(),
            Lists.empty(),
            context,
            null
        );

        this.checkEquals(
            true,
            this.quit
        );
    }

    private boolean quit;

    @Test
    public void testIsPure() {
        this.isPureAndCheck(
            TerminalExpressionFunctionExit.instance(),
            this.createContext(),
            false
        );
    }

    @Override
    public TerminalExpressionFunctionExit<TerminalExpressionEvaluationContext> createBiFunction() {
        return TerminalExpressionFunctionExit.instance();
    }

    @Override
    public TerminalExpressionEvaluationContext createContext() {
        return TerminalExpressionEvaluationContexts.fake();
    }

    @Override
    public int minimumParameterCount() {
        return 0;
    }

    @Override
    public Class<TerminalExpressionFunctionExit<TerminalExpressionEvaluationContext>> type() {
        return Cast.to(TerminalExpressionFunctionExit.class);
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }
}
