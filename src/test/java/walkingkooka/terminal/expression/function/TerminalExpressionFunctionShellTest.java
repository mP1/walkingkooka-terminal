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
import walkingkooka.Either;
import walkingkooka.collect.list.Lists;
import walkingkooka.io.FakeTextReader;
import walkingkooka.io.TextReader;
import walkingkooka.terminal.HasTerminalErrorText;
import walkingkooka.terminal.HasTerminalOutputText;
import walkingkooka.terminal.expression.FakeTerminalExpressionEvaluationContext;
import walkingkooka.terminal.expression.TerminalExpressionEvaluationContext;
import walkingkooka.text.LineEnding;
import walkingkooka.text.printer.Printer;
import walkingkooka.text.printer.Printers;
import walkingkooka.tree.expression.function.ExpressionFunctionTesting;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

public final class TerminalExpressionFunctionShellTest implements ExpressionFunctionTesting<TerminalExpressionFunctionShell<TerminalExpressionEvaluationContext>, Integer, TerminalExpressionEvaluationContext> {

    @Test
    public void testApplyReturnValueConvertToStringFails() {
        final int timeout = 1234;

        final Iterator<String> inputLines = Lists.of(
            "hello",
            "hello2",
            "exit"
        ).iterator();

        final StringBuilder printed = new StringBuilder();

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
            public Printer output() {
                return Printers.sink(LineEnding.NL);
            }

            @Override
            public Printer error() {
                return this.error;
            }

            private final Printer error = Printers.stringBuilder(
                printed,
                LineEnding.NL
            );

            @Override
            public <T> Either<T, String> convert(final Object value,
                                                 final Class<T> target) {
                return this.failConversion(
                    target.cast(value),
                    target
                );
            }

            @Override
            public Object evaluate(final String expression) {
                Objects.requireNonNull(expression, "expression");

                Object value;
                if ("exit".equals(expression)) {
                    this.open = false;
                    value = null;
                } else {
                    value = expression + expression;
                }
                return value;
            }
        };

        this.applyAndCheck(
            TerminalExpressionFunctionShell.instance(),
            Lists.of(timeout),
            context,
            TerminalExpressionFunctionShell.OK_EXIT_CODE
        );

        final String printedString = printed.toString();
        final int eol = printedString.indexOf('\n');

        this.checkEquals(
            "walkingkooka.convert.ConverterException: Failed to convert \"hellohello\" (java.lang.String) to java.lang.String",
            printedString.substring(
                0,
                eol
            ),
            "error"
        );
    }

    @Test
    public void testApplySkipEmptyLine() {
        final int timeout = 1234;

        final Iterator<String> inputLines = Lists.of(
            "",
            " ",
            "exit"
        ).iterator();

        //final StringBuilder printed = new StringBuilder();

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
            public Printer output() {
                return Printers.sink(LineEnding.NL);
                //return this.output;
            }

            @Override
            public Printer error() {
                return Printers.sink(LineEnding.NL);
            }

            @Override
            public Object evaluate(final String expression) {
                Objects.requireNonNull(expression, "expression");

                Object value;
                if ("exit".equals(expression)) {
                    this.open = false;
                    value = null;
                } else {
                    value = expression + expression;
                }
                return value;
            }
        };

        this.applyAndCheck(
            TerminalExpressionFunctionShell.instance(),
            Lists.of(timeout),
            context,
            TerminalExpressionFunctionShell.OK_EXIT_CODE
        );

        // nothing will be printed
    }

    @Test
    public void testApplyBackspaceCharacter() {
        final int timeout = 1234;

        final Iterator<String> inputLines = Lists.of(
            "hel9\u007flo", // the backspace deletes the nine '9' character
            "exit"
        ).iterator();

        final StringBuilder printed = new StringBuilder();

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
            public Printer output() {
                return this.output;
            }

            private final Printer output = Printers.stringBuilder(
                printed,
                LineEnding.NL
            );

            @Override
            public Printer error() {
                return Printers.sink(LineEnding.NL);
            }

            @Override
            public <T> Either<T, String> convert(final Object value,
                                                 final Class<T> target) {
                return this.successfulConversion(
                    target.cast(value),
                    target
                );
            }

            @Override
            public Object evaluate(final String expression) {
                Objects.requireNonNull(expression, "expression");

                Object value;
                if ("exit".equals(expression)) {
                    this.open = false;
                    value = null;
                } else {
                    value = expression + expression;
                }
                return value;
            }
        };

        this.applyAndCheck(
            TerminalExpressionFunctionShell.instance(),
            Lists.of(timeout),
            context,
            TerminalExpressionFunctionShell.OK_EXIT_CODE
        );

        this.checkEquals(
            "hellohello\n",
            printed.toString(),
            "output"
        );
    }

    @Test
    public void testApplyMultipleCommands() {
        final int timeout = 1234;

        final Iterator<String> inputLines = Lists.of(
            "hello",
            "hello2",
            "exit"
        ).iterator();

        final StringBuilder printed = new StringBuilder();

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
            public Printer output() {
                return this.output;
            }

            private final Printer output = Printers.stringBuilder(
                printed,
                LineEnding.NL
            );

            @Override
            public Printer error() {
                return Printers.sink(LineEnding.NL);
            }

            @Override
            public <T> Either<T, String> convert(final Object value,
                                                 final Class<T> target) {
                return this.successfulConversion(
                    target.cast(value),
                    target
                );
            }

            @Override
            public Object evaluate(final String expression) {
                Objects.requireNonNull(expression, "expression");

                Object value;
                if ("exit".equals(expression)) {
                    this.open = false;
                    value = null;
                } else {
                    value = expression + expression;
                }
                return value;
            }
        };

        this.applyAndCheck(
            TerminalExpressionFunctionShell.instance(),
            Lists.of(timeout),
            context,
            TerminalExpressionFunctionShell.OK_EXIT_CODE
        );

        this.checkEquals(
            "hellohello\n" +
                "hello2hello2\n",
            printed.toString(),
            "output"
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

        final StringBuilder printed = new StringBuilder();

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
            public Printer output() {
                return this.output;
            }

            private final Printer output = Printers.stringBuilder(
                printed,
                LineEnding.NL
            );

            @Override
            public Printer error() {
                return Printers.sink(LineEnding.NL);
            }

            @Override
            public <T> Either<T, String> convert(final Object value,
                                                 final Class<T> target) {
                return this.successfulConversion(
                    target.cast(value),
                    target
                );
            }

            @Override
            public Object evaluate(final String expression) {
                Objects.requireNonNull(expression, "expression");

                Object value;
                if ("exit".equals(expression)) {
                    this.open = false;
                    value = null;
                } else {
                    value = expression + expression;
                }
                return value;
            }
        };

        this.applyAndCheck(
            TerminalExpressionFunctionShell.instance(),
            Lists.of(timeout),
            context,
            TerminalExpressionFunctionShell.OK_EXIT_CODE
        );

        this.checkEquals(
            "helloworldhelloworld\n",
            printed.toString(),
            "output"
        );
    }

    @Test
    public void testApplyHasTerminalOutputText() {
        final int timeout = 1234;

        final Iterator<String> inputLines = Lists.of(
            "hello"
        ).iterator();

        final StringBuilder printed = new StringBuilder();

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
            public Printer output() {
                return this.output;
            }

            private final Printer output = Printers.stringBuilder(
                printed,
                LineEnding.NL
            );

            @Override
            public Printer error() {
                return Printers.sink(LineEnding.NL);
            }

            @Override
            public Object evaluate(final String expression) {
                Objects.requireNonNull(expression, "expression");

                this.open = false; // need to also kill shell
                return new HasTerminalOutputText() {
                    @Override
                    public String terminalOutputText() {
                        return "World";
                    }
                };
            }
        };

        this.applyAndCheck(
            TerminalExpressionFunctionShell.instance(),
            Lists.of(timeout),
            context,
            TerminalExpressionFunctionShell.OK_EXIT_CODE
        );

        this.checkEquals(
            "World\n",
            printed.toString(),
            "output"
        );
    }

    @Test
    public void testApplyHasTerminalErrorText() {
        final int timeout = 1234;

        final Iterator<String> inputLines = Lists.of(
            "hello"
        ).iterator();

        final StringBuilder printed = new StringBuilder();

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
            public Printer error() {
                return this.error;
            }

            private final Printer error = Printers.stringBuilder(
                printed,
                LineEnding.NL
            );

            @Override
            public Printer output() {
                return Printers.sink(LineEnding.NL);
            }

            @Override
            public Object evaluate(final String expression) {
                Objects.requireNonNull(expression, "expression");

                this.open = false; // need to also kill shell
                return new HasTerminalErrorText() {
                    @Override
                    public String terminalErrorText() {
                        return "World";
                    }
                };
            }
        };

        this.applyAndCheck(
            TerminalExpressionFunctionShell.instance(),
            Lists.of(timeout),
            context,
            TerminalExpressionFunctionShell.OK_EXIT_CODE
        );

        this.checkEquals(
            "World\n",
            printed.toString(),
            "error"
        );
    }

    @Test
    public void testApplyFunctionThrowsPrintsStackTrace() {
        final int timeout = 1234;

        final Iterator<String> inputLines = Lists.of(
            "throw",
            "exit"
        ).iterator();

        final StringBuilder printed = new StringBuilder();

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
            public Printer output() {
                return Printers.sink(LineEnding.NL);
            }

            @Override
            public Printer error() {
                return this.error;
            }

            private final Printer error = Printers.stringBuilder(
                printed,
                LineEnding.NL
            );

            @Override
            public <T> Either<T, String> convert(final Object value,
                                                 final Class<T> target) {
                return this.failConversion(
                    target.cast(value),
                    target
                );
            }

            @Override
            public Object evaluate(final String expression) {
                Objects.requireNonNull(expression, "expression");

                switch (expression) {
                    case "exit":
                        this.open = false;
                        return null;
                    case "throw":
                        throw new RuntimeException("Hello World") {

                            @Override
                            public void printStackTrace(final PrintStream p) {
                                p.println("Stacktrace 123");
                            }
                        };
                    default:
                        throw new UnsupportedOperationException();
                }
            }
        };

        this.applyAndCheck(
            TerminalExpressionFunctionShell.instance(),
            Lists.of(timeout),
            context,
            TerminalExpressionFunctionShell.OK_EXIT_CODE
        );

        this.checkEquals(
            "Stacktrace 123\n",
            printed.toString(),
            "error"
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
