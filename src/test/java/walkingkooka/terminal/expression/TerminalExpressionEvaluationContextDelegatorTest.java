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

import walkingkooka.convert.ConverterContexts;
import walkingkooka.convert.Converters;
import walkingkooka.datetime.DateTimeContexts;
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.environment.EnvironmentContextDelegator;
import walkingkooka.environment.EnvironmentContexts;
import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.locale.LocaleContexts;
import walkingkooka.math.DecimalNumberContext;
import walkingkooka.math.DecimalNumberContexts;
import walkingkooka.terminal.TerminalContext;
import walkingkooka.terminal.TerminalContextDelegator;
import walkingkooka.terminal.TerminalContexts;
import walkingkooka.terminal.expression.TerminalExpressionEvaluationContextDelegatorTest.TestTerminalExpressionEvaluationContextDelegator;
import walkingkooka.text.CaseSensitivity;
import walkingkooka.text.LineEnding;
import walkingkooka.text.printer.Printers;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.ExpressionEvaluationContextDelegator;
import walkingkooka.tree.expression.ExpressionEvaluationContexts;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.expression.ExpressionReference;

import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public final class TerminalExpressionEvaluationContextDelegatorTest implements TerminalExpressionEvaluationContextTesting<TestTerminalExpressionEvaluationContextDelegator> {

    @Override
    public void testEvaluateExpressionUnknownFunctionNameFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testEnterScopeWithNullFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testEnterScopeGivesDifferentInstance() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testReferenceWithNullReferenceFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TestTerminalExpressionEvaluationContextDelegator createContext() {
        return new TestTerminalExpressionEvaluationContextDelegator();
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

    final static class TestTerminalExpressionEvaluationContextDelegator implements TerminalExpressionEvaluationContextDelegator {
        @Override
        public TerminalExpressionEvaluationContext terminalExpressionEvaluationContext() {
            return new TestTerminalExpressionEvaluationContext();
        }

        @Override
        public ExpressionEvaluationContext enterScope(final Function<ExpressionReference, Optional<Optional<Object>>> function) {
            return this;
        }

        @Override
        public Optional<Optional<Object>> reference(final ExpressionReference expressionReference) {
            return Optional.empty();
        }

        @Override
        public TerminalExpressionEvaluationContext setLocale(final Locale locale) {
            Objects.requireNonNull(locale, "locale");
            throw new UnsupportedOperationException();
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }

    final static class TestTerminalExpressionEvaluationContext implements TerminalExpressionEvaluationContext,
        ExpressionEvaluationContextDelegator,
        TerminalContextDelegator,
        EnvironmentContextDelegator {

        // EnvironmentContextDelegator..................................................................................

        @Override
        public LocalDateTime now() {
            return LocalDateTime.now();
        }

        @Override
        public <T> TerminalExpressionEvaluationContext setEnvironmentValue(final EnvironmentValueName<T> name,
                                                                           final T value) {
            this.environmentContext().setEnvironmentValue(name, value);
            return this;
        }

        @Override
        public TerminalExpressionEvaluationContext removeEnvironmentValue(EnvironmentValueName<?> name) {
            this.environmentContext().removeEnvironmentValue(name);
            return this;
        }

        @Override
        public EnvironmentContext environmentContext() {
            return EnvironmentContexts.empty(
                LocalDateTime::now,
                Optional.empty()
            );
        }

        // TerminalContextDelegator.....................................................................................

        @Override
        public TerminalContext terminalContext() {
            return TerminalContexts.printer(
                Printers.sink(LineEnding.NONE)
            );
        }

        // ExpressionEvaluationContextDelegator.........................................................................

        @Override
        public ExpressionEvaluationContext enterScope(final Function<ExpressionReference, Optional<Optional<Object>>> variables) {
            Objects.requireNonNull(variables, "variables");
            return this;
        }

        @Override
        public Optional<Optional<Object>> reference(final ExpressionReference expressionReference) {
            return Optional.empty();
        }

        @Override
        public ExpressionEvaluationContext expressionEvaluationContext() {
            return ExpressionEvaluationContexts.basic(
                ExpressionNumberKind.BIG_DECIMAL,
                name -> {
                    Objects.requireNonNull(name, "name");
                    throw new UnsupportedOperationException();
                },
                (r) -> {
                    throw new UnsupportedOperationException();
                },
                (r) -> {
                    throw new UnsupportedOperationException();
                },
                ExpressionEvaluationContexts.referenceNotFound(),
                CaseSensitivity.INSENSITIVE,
                ConverterContexts.basic(
                    Converters.EXCEL_1904_DATE_SYSTEM_OFFSET,
                    Converters.simple(),
                    DateTimeContexts.fake(),
                    DECIMAL_NUMBER_CONTEXT
                ),
                LocaleContexts.jre(Locale.ENGLISH)
            );
        }

        // LocaleContext....................................................................................................

        @Override
        public TerminalExpressionEvaluationContext setLocale(final Locale locale) {
            throw new UnsupportedOperationException();
        }
    }

    // class............................................................................................................

    @Override
    public Class<TestTerminalExpressionEvaluationContextDelegator> type() {
        return TestTerminalExpressionEvaluationContextDelegator.class;
    }
}
