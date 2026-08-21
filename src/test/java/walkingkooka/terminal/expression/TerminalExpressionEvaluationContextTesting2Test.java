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

package walkingkooka.terminal.expression;

import walkingkooka.convert.BinaryNumberConverterFunctions;
import walkingkooka.convert.ConverterContexts;
import walkingkooka.convert.Converters;
import walkingkooka.currency.CurrencyLocaleContexts;
import walkingkooka.datetime.DateTimeContextTesting;
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.environment.EnvironmentContextDelegator;
import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.math.DecimalNumberContext;
import walkingkooka.math.DecimalNumberContextDelegator;
import walkingkooka.storage.StorageEnvironmentContext;
import walkingkooka.storage.StorageEnvironmentContexts;
import walkingkooka.terminal.TerminalContext;
import walkingkooka.terminal.TerminalContextDelegator;
import walkingkooka.terminal.TerminalContexts;
import walkingkooka.terminal.TerminalId;
import walkingkooka.terminal.expression.TerminalExpressionEvaluationContextTesting2Test.TestTerminalExpressionEvaluationContext;
import walkingkooka.text.CaseSensitivity;
import walkingkooka.tree.expression.CanEvaluateString;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.ExpressionEvaluationContextDelegator;
import walkingkooka.tree.expression.ExpressionEvaluationContexts;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.expression.ExpressionReference;
import walkingkooka.tree.expression.convert.ExpressionNumberConverterContexts;

import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class TerminalExpressionEvaluationContextTesting2Test implements TerminalExpressionEvaluationContextTesting2<TestTerminalExpressionEvaluationContext>,
    DateTimeContextTesting,
    DecimalNumberContextDelegator {

    @Override
    public void testSetEnvironmentContextWithEqualEnvironmentContext() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testSetLineEndingWithDifferentAndWatcher() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TestTerminalExpressionEvaluationContext createContext() {
        return new TestTerminalExpressionEvaluationContext();
    }

    @Override
    public MathContext mathContext() {
        return MATH_CONTEXT;
    }

    @Override
    public int decimalNumberDigitCount() {
        return DEFAULT_NUMBER_DIGIT_COUNT;
    }

    @Override
    public DecimalNumberContext decimalNumberContext() {
        return DECIMAL_NUMBER_CONTEXT;
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
        public void exitTerminal(final Object exitValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public TerminalContext terminalContext() {
            return TerminalContexts.system(
                TerminalId.with(1),
                (e, c) -> {
                    throw new UnsupportedOperationException();
                },
                (e) -> {
                    throw new UnsupportedOperationException();
                },
                StorageEnvironmentContexts.fake()
            );
        }

        // EnvironmentContext...........................................................................................

        @Override
        public TerminalExpressionEvaluationContext cloneEnvironment() {
            throw new UnsupportedOperationException();
        }

        @Override
        public TerminalExpressionEvaluationContext setEnvironmentContext(final EnvironmentContext environmentContext) {
            Objects.requireNonNull(environmentContext, "environmentContext");

            throw new UnsupportedOperationException();
        }

        @Override
        public Locale locale() {
            return this.storageEnvironmentContext.locale();
        }

        @Override
        public <T> void setEnvironmentValue(final EnvironmentValueName<T> name,
                                            final T value) {
            this.storageEnvironmentContext.setEnvironmentValue(
                name,
                value
            );
        }

        @Override
        public void removeEnvironmentValue(final EnvironmentValueName<?> name) {
            this.storageEnvironmentContext.removeEnvironmentValue(name);
        }

        @Override
        public LocalDateTime now() {
            return this.storageEnvironmentContext.now();
        }

        @Override
        public EnvironmentContext environmentContext() {
            return this.storageEnvironmentContext;
        }

        private final StorageEnvironmentContext storageEnvironmentContext = STORAGE_ENVIRONMENT_CONTEXT.cloneEnvironment();

        // CanEvaluateStringDelegator...................................................................................

        @Override
        public CanEvaluateString canEvaluateString() {
            throw new UnsupportedOperationException();
        }

        // ExpressionEvaluationContextDelegator.........................................................................

        @Override
        public Object evaluate(final String expression) {
            Objects.requireNonNull(expression, "expression");

            throw new UnsupportedOperationException();
        }

        @Override
        public ExpressionEvaluationContext expressionEvaluationContext() {
            return ExpressionEvaluationContexts.basic(
                EXPRESSION_NUMBER_KIND,
                (e, c) -> {
                    Objects.requireNonNull(e, "expression");
                    throw new UnsupportedOperationException();
                },
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
                    BinaryNumberConverterFunctions.fake(), // multiplier
                    ConverterContexts.basic(
                        false, // canNumbersHaveGroupSeparator
                        Converters.EXCEL_1904_DATE_SYSTEM_OFFSET, // dateTimeOffset
                        ',', // valueSeparator
                        Converters.simple(),
                        BinaryNumberConverterFunctions.fake(), // multiplier
                        BINARY_TEXT_CONTEXT,
                        CurrencyLocaleContexts.fake(),
                        DATE_TIME_CONTEXT,
                        DECIMAL_NUMBER_CONTEXT
                    ),
                    ExpressionNumberKind.DEFAULT
                ),
                STORAGE_ENVIRONMENT_CONTEXT.cloneEnvironment(),
                LOCALE_CONTEXT
            );
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }
}
