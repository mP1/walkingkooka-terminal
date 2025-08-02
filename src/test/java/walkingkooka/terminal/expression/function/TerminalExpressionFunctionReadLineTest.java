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
import walkingkooka.tree.expression.function.ExpressionFunctionTesting;

import java.util.Optional;

public final class TerminalExpressionFunctionReadLineTest implements ExpressionFunctionTesting<TerminalExpressionFunctionReadLine<TerminalExpressionEvaluationContext>, String, TerminalExpressionEvaluationContext> {

    @Test
    public void testApplyWithTimeout() {
        final long timeout = 1234;
        final String line = "abc";

        final TerminalExpressionEvaluationContext context = new FakeTerminalExpressionEvaluationContext() {

            @Override
            public Optional<String> readLine(final long t) {
                return Optional.of(line + t);
            }
        };

        this.applyAndCheck(
            TerminalExpressionFunctionReadLine.instance(),
            Lists.of(timeout),
            context,
            line + timeout
        );
    }

    @Test
    public void testApplyMissingTimeoutParameter() {
        final String line = "abc";

        final TerminalExpressionEvaluationContext context = new FakeTerminalExpressionEvaluationContext() {

            @Override
            public Optional<String> readLine(final long t) {
                return Optional.of(line + t);
            }
        };

        this.applyAndCheck(
            TerminalExpressionFunctionReadLine.instance(),
            Lists.empty(),
            context,
            line + TerminalExpressionFunctionReadLine.DEFAULT_TIMEOUT
        );
    }

    @Test
    public void testIsPure() {
        this.isPureAndCheck(
            TerminalExpressionFunctionReadLine.instance(),
            this.createContext(),
            false
        );
    }

    @Override
    public TerminalExpressionFunctionReadLine<TerminalExpressionEvaluationContext> createBiFunction() {
        return TerminalExpressionFunctionReadLine.instance();
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
    public Class<TerminalExpressionFunctionReadLine<TerminalExpressionEvaluationContext>> type() {
        return Cast.to(TerminalExpressionFunctionReadLine.class);
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }
}
