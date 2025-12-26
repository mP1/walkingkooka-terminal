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
import walkingkooka.terminal.HasTerminalOutputText;
import walkingkooka.terminal.expression.TerminalExpressionEvaluationContext;
import walkingkooka.text.CharSequences;
import walkingkooka.text.printer.Printer;
import walkingkooka.tree.expression.ExpressionPurityContext;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterKind;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterName;

import java.util.List;
import java.util.Optional;

/**
 * A very simple shell or REPL that tries to read a line of text with support for line-continuation. Each complete input is then
 * {@link TerminalExpressionEvaluationContext#evaluate(String)}. Note input or output redirection is not supported.
 * <br>
 * If an exit command is executed, the {@link TerminalExpressionEvaluationContext#isTerminalOpen()}, will become false
 * and reading of input will stop and the function exited with a code of 0.
 */
final class TerminalExpressionFunctionShell<C extends TerminalExpressionEvaluationContext> extends TerminalExpressionFunction<Integer, C> {

    /**
     * Type safe instance getter.
     */
    static <C extends TerminalExpressionEvaluationContext> TerminalExpressionFunctionShell<C> instance() {
        return Cast.to(INSTANCE);
    }

    private final static TerminalExpressionFunctionShell INSTANCE = new TerminalExpressionFunctionShell<>();

    private TerminalExpressionFunctionShell() {
        super("shell");
    }

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return PARAMETERS;
    }

    private final static ExpressionFunctionParameter<Integer> TIMEOUT = ExpressionFunctionParameterName.with("timeout")
        .optional(Integer.class)
        .setDefaultValue(Optional.of(50))
        .setKinds(ExpressionFunctionParameterKind.CONVERT_EVALUATE_RESOLVE_REFERENCES);

    private final static List<ExpressionFunctionParameter<?>> PARAMETERS = Lists.of(TIMEOUT);

    @Override
    public Class<Integer> returnType() {
        return Integer.class;
    }

    final static int OK_EXIT_CODE = 0;

    @Override
    public Integer apply(final List<Object> parameters,
                         final C context) {
        this.checkParameterCount(parameters);

        final int timeout = TIMEOUT.getOrFail(
            parameters,
            0
        );
        if (timeout <= 0L) {
            throw new IllegalArgumentException("Invalid Timeout " + timeout + " <= 0");
        }

        StringBuilder buffer = new StringBuilder();
        final Printer output = context.output();
        final Printer error = context.error();

        while (context.isTerminalOpen()) {
            final String line = context.input()
                .readLine(timeout)
                .orElse(null);

            if (null != line) {
                final boolean lineContinued = line.endsWith(LINE_CONTINUATION);
                final int lineLength = line.length();

                buffer.append(
                    line,
                    0,
                    lineContinued ?
                        lineLength - 1 :
                        lineLength
                );

                if (lineContinued) {
                    continue;
                }

                try {
                    final Object value = context.evaluate(
                        CharSequences.unescape(
                            buffer.toString()
                        ).toString()
                    );
                    if (null != value) {
                        try {
                            final String valueAsString;

                            // print SpreadsheetError#message which has detailed message
                            // #NAME?
                            if (value instanceof HasTerminalOutputText) {
                                final HasTerminalOutputText hasTerminalOutputText = (HasTerminalOutputText) value;
                                valueAsString = hasTerminalOutputText.terminalOutputText();
                            } else {
                                valueAsString = context.convertOrFail(
                                    value,
                                    String.class
                                );
                            }

                            output.println(valueAsString);
                        } catch (final UnsupportedOperationException rethrow) {
                            throw rethrow;
                        } catch (final RuntimeException cause) {
                            error.println(cause.getMessage());
                            error.flush();
                        }
                    }
                } finally {
                    output.flush(); // evaluated expression/function might have printed but not flushed.
                    error.flush();
                }

                buffer = new StringBuilder();
            }
        }

        return 0;
    }

    private final static String LINE_CONTINUATION = "\\";

    @Override
    public boolean isPure(final ExpressionPurityContext expressionPurityContext) {
        return false;
    }
}
