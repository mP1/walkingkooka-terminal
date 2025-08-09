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

import walkingkooka.convert.ConverterContexts;
import walkingkooka.convert.Converters;
import walkingkooka.datetime.DateTimeContexts;
import walkingkooka.datetime.DateTimeSymbols;
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.environment.EnvironmentContextDelegator;
import walkingkooka.environment.EnvironmentContexts;
import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.locale.LocaleContexts;
import walkingkooka.math.DecimalNumberContext;
import walkingkooka.math.DecimalNumberContexts;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.terminal.TerminalContext;
import walkingkooka.terminal.TerminalContexts;
import walkingkooka.terminal.expression.TerminalContextDelegator;
import walkingkooka.terminal.expression.function.TerminalExpressionEvaluationContextTestingTest.TestTerminalExpressionEvaluationContext;
import walkingkooka.text.CaseSensitivity;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.ExpressionEvaluationContextDelegator;
import walkingkooka.tree.expression.ExpressionEvaluationContexts;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.expression.ExpressionReference;
import walkingkooka.tree.expression.convert.ExpressionNumberConverterContexts;

import java.math.MathContext;
import java.text.DateFormatSymbols;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class TerminalExpressionEvaluationContextTestingTest implements TerminalExpressionEvaluationContextTesting<TestTerminalExpressionEvaluationContext> {

    @Override
    public TestTerminalExpressionEvaluationContext createContext() {
        return new TestTerminalExpressionEvaluationContext();
    }

    private final static DecimalNumberContext DECIMAL_NUMBER_CONTEXT = DecimalNumberContexts.american(MathContext.DECIMAL32);

    @Override
    public MathContext mathContext() {
        return DECIMAL_NUMBER_CONTEXT.mathContext();
    }

    @Override
    public String currencySymbol() {
        return DECIMAL_NUMBER_CONTEXT.currencySymbol();
    }

    @Override
    public char decimalSeparator() {
        return DECIMAL_NUMBER_CONTEXT.decimalSeparator();
    }

    @Override
    public String exponentSymbol() {
        return DECIMAL_NUMBER_CONTEXT.exponentSymbol();
    }

    @Override
    public char groupSeparator() {
        return DECIMAL_NUMBER_CONTEXT.groupSeparator();
    }

    @Override
    public String infinitySymbol() {
        return DECIMAL_NUMBER_CONTEXT.infinitySymbol();
    }

    @Override
    public char monetaryDecimalSeparator() {
        return DECIMAL_NUMBER_CONTEXT.monetaryDecimalSeparator();
    }

    @Override
    public String nanSymbol() {
        return DECIMAL_NUMBER_CONTEXT.nanSymbol();
    }

    @Override
    public char percentSymbol() {
        return DECIMAL_NUMBER_CONTEXT.percentSymbol();
    }

    @Override
    public char permillSymbol() {
        return DECIMAL_NUMBER_CONTEXT.permillSymbol();
    }

    @Override
    public char negativeSign() {
        return DECIMAL_NUMBER_CONTEXT.negativeSign();
    }

    @Override
    public char positiveSign() {
        return DECIMAL_NUMBER_CONTEXT.positiveSign();
    }

    @Override
    public char zeroDigit() {
        return DECIMAL_NUMBER_CONTEXT.zeroDigit();
    }

    @Override
    public void testEnterScopeGivesDifferentInstance() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testEvaluateExpressionUnknownFunctionNameFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testExpressionFunctionWithNullFunctionNameFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testIsPureNullNameFails() {
        throw new UnsupportedOperationException();
    }

    // class............................................................................................................

    @Override
    public Class<TestTerminalExpressionEvaluationContext> type() {
        return TestTerminalExpressionEvaluationContext.class;
    }

    static class TestTerminalExpressionEvaluationContext implements TerminalExpressionEvaluationContext,
        ExpressionEvaluationContextDelegator,
        TerminalContextDelegator,
        EnvironmentContextDelegator {

        @Override
        public ExpressionEvaluationContext enterScope(final Function<ExpressionReference, Optional<Optional<Object>>> scoped) {
            Objects.requireNonNull(scoped);
            throw new UnsupportedOperationException();
        }

        @Override
        public Optional<Optional<Object>> reference(final ExpressionReference reference) {
            Objects.requireNonNull(reference);
            throw new UnsupportedOperationException();
        }

        // TerminalContextDelegator.....................................................................................

        @Override
        public TerminalContext terminalContext() {
            return TerminalContexts.system();
        }

        // EnvironmentContext...........................................................................................

        @Override
        public <T> TestTerminalExpressionEvaluationContext setEnvironmentValue(final EnvironmentValueName<T> name,
                                                                               final T value) {
            Objects.requireNonNull(name, "name");
            Objects.requireNonNull(value, "value");

            throw new UnsupportedOperationException();
        }

        @Override
        public LocalDateTime now() {
            throw new UnsupportedOperationException();
        }

        @Override
        public EnvironmentContext environmentContext() {
            return EnvironmentContexts.empty(
                LocalDateTime::now,
                Optional.of(
                    EmailAddress.parse("user@example.com")
                )
            );
        }

        // ExpressionEvaluationContextDelegator.........................................................................

        @Override
        public ExpressionEvaluationContext expressionEvaluationContext() {
            final Locale locale = Locale.ENGLISH;

            return ExpressionEvaluationContexts.basic(
                ExpressionNumberKind.BIG_DECIMAL,
                (n) -> {
                    throw new UnsupportedOperationException();
                },
                (e) -> {
                    e.printStackTrace();
                    throw new UnsupportedOperationException();
                },
                (r) -> {
                    throw new UnsupportedOperationException();
                },
                (r) -> {
                    throw new UnsupportedOperationException();
                },
                CaseSensitivity.SENSITIVE,
                ExpressionNumberConverterContexts.basic(
                    Converters.simple(),
                    ConverterContexts.basic(
                        Converters.EXCEL_1904_DATE_SYSTEM_OFFSET, // dateTimeOffset
                        Converters.simple(),
                        DateTimeContexts.basic(
                            DateTimeSymbols.fromDateFormatSymbols(
                                new DateFormatSymbols(locale)
                            ),
                            locale,
                            1950, // defaultYear
                            50, // twoDigitYear
                            EnvironmentContexts.fake()
                        ),
                        DECIMAL_NUMBER_CONTEXT
                    ),
                    ExpressionNumberKind.DEFAULT
                ),
                LocaleContexts.jre(locale)
            );
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }
}
