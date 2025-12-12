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
import walkingkooka.io.FakeTextReader;
import walkingkooka.io.TextReader;
import walkingkooka.terminal.expression.FakeTerminalExpressionEvaluationContext;
import walkingkooka.terminal.expression.TerminalExpressionEvaluationContext;
import walkingkooka.tree.expression.function.ExpressionFunctionTesting;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class TerminalExpressionFunctionShellTest implements ExpressionFunctionTesting<TerminalExpressionFunctionShell<TerminalExpressionEvaluationContext>, Integer, TerminalExpressionEvaluationContext> {

    @Test
    public void testApply() {
        final int timeout = 1234;

        final Iterator<String> inputLines = Lists.of(
            "hello",
            "hello2",
            "exit"
        ).iterator();

        final List<String> printed = Lists.array();

        final TerminalExpressionEvaluationContext context = new FakeTerminalExpressionEvaluationContext() {

            @Override
            public boolean isTerminalOpen() {
                return this.open;
            }

            private boolean open = true;

            @Override
            public TextReader input() {
                return new FakeTextReader() {

                    @Override
                    public Optional<String> readLine(final long timeout) {
                        return Optional.ofNullable(
                            inputLines.hasNext() ?
                                inputLines.next() :
                                null
                        );
                    }
                };
            }

            @Override
            public Object evaluate(final String expression) {
                Objects.requireNonNull(expression, "expression");

                if ("exit".equals(expression)) {
                    this.open = false;
                } else {
                    printed.add(expression + expression);
                }
                return 1;
            }
        };

        this.applyAndCheck(
            TerminalExpressionFunctionShell.instance(),
            Lists.of(timeout),
            context,
            TerminalExpressionFunctionShell.OK_EXIT_CODE
        );

        this.checkEquals(
            Lists.of(
                "hellohello",
                "hello2hello2"
            ),
            printed,
            "printed"
        );
    }

    @Test
    public void testApplyIncludesLineContinuation() {
        final int timeout = 1234;

        final Iterator<String> inputLines = Lists.of(
            "hello\\",
            "world",
            "exit"
        ).iterator();

        final List<String> printed = Lists.array();

        final TerminalExpressionEvaluationContext context = new FakeTerminalExpressionEvaluationContext() {

            @Override
            public boolean isTerminalOpen() {
                return this.open;
            }

            private boolean open = true;

            @Override
            public TextReader input() {
                return new FakeTextReader() {

                    @Override
                    public Optional<String> readLine(final long timeout) {
                        return Optional.ofNullable(
                            inputLines.hasNext() ?
                                inputLines.next() :
                                null
                        );
                    }
                };
            }

            @Override
            public Object evaluate(final String expression) {
                Objects.requireNonNull(expression, "expression");

                if ("exit".equals(expression)) {
                    this.open = false;
                } else {
                    printed.add(expression + expression);
                }
                return 1;
            }
        };

        this.applyAndCheck(
            TerminalExpressionFunctionShell.instance(),
            Lists.of(timeout),
            context,
            TerminalExpressionFunctionShell.OK_EXIT_CODE
        );

        this.checkEquals(
            Lists.of(
                "helloworldhelloworld"
            ),
            printed,
            "printed"
        );
    }

    @Test
    public void testIsPure() {
        this.isPureAndCheck(
            TerminalExpressionFunctionShell.instance(),
            this.createContext(),
            false
        );
    }

    @Override
    public TerminalExpressionFunctionShell<TerminalExpressionEvaluationContext> createBiFunction() {
        return TerminalExpressionFunctionShell.instance();
    }

    @Override
    public TerminalExpressionEvaluationContext createContext() {
        return new FakeTerminalExpressionEvaluationContext() {

            @Override
            public boolean isTerminalOpen() {
                return false;
            }
        };
    }

    @Override
    public int minimumParameterCount() {
        return 0;
    }

    // Class............................................................................................................

    @Override
    public Class<TerminalExpressionFunctionShell<TerminalExpressionEvaluationContext>> type() {
        return Cast.to(TerminalExpressionFunctionShell.class);
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }
}
