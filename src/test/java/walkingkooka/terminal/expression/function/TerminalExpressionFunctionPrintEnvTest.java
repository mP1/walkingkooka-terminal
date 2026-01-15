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
import walkingkooka.convert.Converter;
import walkingkooka.convert.Converters;
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.environment.EnvironmentContexts;
import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.terminal.expression.FakeTerminalExpressionEvaluationContext;
import walkingkooka.terminal.expression.TerminalExpressionEvaluationContext;
import walkingkooka.text.LineEnding;
import walkingkooka.text.printer.Printer;
import walkingkooka.text.printer.Printers;
import walkingkooka.tree.expression.function.ExpressionFunction;
import walkingkooka.tree.expression.function.ExpressionFunctionTesting;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

public final class TerminalExpressionFunctionPrintEnvTest implements ExpressionFunctionTesting<TerminalExpressionFunctionPrintEnv<TerminalExpressionEvaluationContext>, Void, TerminalExpressionEvaluationContext> {

    private final static EnvironmentValueName<Integer> NUMBER_VALUE = EnvironmentValueName.with(
        "number-value",
        Integer.class
    );

    private final static EnvironmentValueName<String> STRING_VALUE = EnvironmentValueName.with(
        "string-value",
        String.class
    );

    private final static LineEnding LINE_ENDING = LineEnding.NL;

    private final static Locale LOCALE = Locale.forLanguageTag("en-AU");

    @Test
    public void testApplyWithoutAnyEnvironmentValueNames() {
        this.applyAndCheck(
            ExpressionFunction.NO_PARAMETER_VALUES,
            "lineEnding=\\n\n" +
                "locale=en_AU\n" +
                "now=1999-12-31T12:58\n" +
                "number-value=456\n" +
                "string-value=hello\n" +
                "user=user@example.com\n"
        );
    }

    @Test
    public void testApplyWithUnknownEnvironmentValue2() {
        this.applyAndCheck(
            Lists.of(
                EnvironmentValueName.with(
                    "Unknown",
                    Object.class
                )
            ),
            "" // nothing printed
        );
    }

    @Test
    public void testApplyWithExistingEnvironmentValue() {
        this.applyAndCheck(
            Lists.of(
                NUMBER_VALUE
            ),
            "456\n"
        );
    }

    @Test
    public void testApplyWithExistingEnvironmentValue2() {
        this.applyAndCheck(
            Lists.of(
                STRING_VALUE
            ),
            "hello\n"
        );
    }

    @Test
    public void testApplyWithLineEnding() {
        this.applyAndCheck(
            Lists.of(
                EnvironmentContext.LINE_ENDING
            ),
            "\\n\n"
        );
    }

    private void applyAndCheck(final List<Object> values,
                               final String expected) {
        final StringBuilder printed = new StringBuilder();
        final TerminalExpressionEvaluationContext context = this.createContext(printed);

        this.applyAndCheck(
            values,
            context,
            null
        );

        this.checkEquals(
            expected,
            printed.toString()
        );
    }

    @Test
    public void testIsPure() {
        this.isPureAndCheck(
            TerminalExpressionFunctionPrintEnv.instance(),
            this.createContext(),
            false
        );
    }

    @Override
    public TerminalExpressionFunctionPrintEnv<TerminalExpressionEvaluationContext> createBiFunction() {
        return TerminalExpressionFunctionPrintEnv.instance();
    }

    @Override
    public TerminalExpressionEvaluationContext createContext() {
        return this.createContext(
            new StringBuilder()
        );
    }

    private TerminalExpressionEvaluationContext createContext(final StringBuilder output) {
        return new FakeTerminalExpressionEvaluationContext() {

            @Override
            public Printer output() {
                return Printers.stringBuilder(
                    output,
                    TerminalExpressionFunctionPrintEnvTest.LINE_ENDING
                );
            }

            @Override
            public <T> Optional<T> environmentValue(final EnvironmentValueName<T> name) {
                return this.environmentContext.environmentValue(name);
            }

            @Override
            public Set<EnvironmentValueName<?>> environmentValueNames() {
                return this.environmentContext.environmentValueNames();
            }

            {
                final EnvironmentContext context = EnvironmentContexts.map(
                    EnvironmentContexts.empty(
                        TerminalExpressionFunctionPrintEnvTest.LINE_ENDING,
                        TerminalExpressionFunctionPrintEnvTest.LOCALE,
                        () -> LocalDateTime.of(
                            1999,
                            12,
                            31,
                            12,
                            58
                        ),
                        Optional.of(
                            EmailAddress.parse("user@example.com")
                        )
                    )
                );
                context.setEnvironmentValue(
                    STRING_VALUE,
                    "hello"
                );
                context.setEnvironmentValue(
                    NUMBER_VALUE,
                    456
                );

                this.environmentContext = context;
            }

            private final EnvironmentContext environmentContext;

            @Override
            public <T> Either<T, String> convert(final Object value,
                                                 final Class<T> target) {
                return this.converter.convert(
                    value,
                    target,
                    this
                );
            }

            private final Converter<TerminalExpressionEvaluationContext> converter = Converters.collection(
                Lists.of(
                    Converters.stringToCharacterOrString(),
                    Converters.numberToString(
                        (c) ->
                            (DecimalFormat) DecimalFormat.getInstance(TerminalExpressionFunctionPrintEnvTest.LOCALE)
                    ),
                    Converters.objectToString()
                )
            );
        };
    }

    @Override
    public int minimumParameterCount() {
        return 0;
    }

    // class............................................................................................................

    @Override
    public Class<TerminalExpressionFunctionPrintEnv<TerminalExpressionEvaluationContext>> type() {
        return Cast.to(TerminalExpressionFunctionPrintEnv.class);
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }
}
