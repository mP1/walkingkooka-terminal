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

package walkingkooka.terminal;

import org.junit.jupiter.api.Test;
import walkingkooka.ToStringTesting;
import walkingkooka.environment.HasUser;
import walkingkooka.io.TextReader;
import walkingkooka.io.TextReaders;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.terminal.expression.TerminalExpressionEvaluationContext;
import walkingkooka.text.printer.Printer;
import walkingkooka.text.printer.Printers;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class BasicTerminalContextTest implements TerminalContextTesting<BasicTerminalContext>,
    ToStringTesting<BasicTerminalContext> {

    private final static TerminalId TERMINAL_ID = TerminalId.parse("123");

    private final static HasUser HAS_USER = () -> Optional.of(
        EmailAddress.parse("user@example.com")
    );

    private final static TextReader INPUT = TextReaders.fake();

    private final static Printer OUTPUT = Printers.fake();

    private final static Printer ERROR = Printers.fake();

    private final static BiFunction<String, TerminalContext, Object> EVALUATOR = (e, c) -> {
        throw new UnsupportedOperationException();
    };

    private final static Function<TerminalContext, TerminalExpressionEvaluationContext> EXPRESSION_EVALUATION_CONTEXT_FACTORY = (c) -> {
        throw new UnsupportedOperationException();
    };

    @Test
    public void testWithNullTerminalIdFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicTerminalContext.with(
                null,
                HAS_USER,
                INPUT,
                OUTPUT,
                ERROR,
                EVALUATOR,
                EXPRESSION_EVALUATION_CONTEXT_FACTORY
            )
        );
    }

    @Test
    public void testWithNullHasUserFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicTerminalContext.with(
                TERMINAL_ID,
                null,
                INPUT,
                OUTPUT,
                ERROR,
                EVALUATOR,
                EXPRESSION_EVALUATION_CONTEXT_FACTORY
            )
        );
    }

    @Test
    public void testWithNullInputFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicTerminalContext.with(
                TERMINAL_ID,
                HAS_USER,
                null,
                OUTPUT,
                ERROR,
                EVALUATOR,
                EXPRESSION_EVALUATION_CONTEXT_FACTORY
            )
        );
    }

    @Test
    public void testWithNullOutputFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicTerminalContext.with(
                TERMINAL_ID,
                HAS_USER,
                INPUT,
                null,
                ERROR,
                EVALUATOR,
                EXPRESSION_EVALUATION_CONTEXT_FACTORY
            )
        );
    }

    @Test
    public void testWithNullErrorFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicTerminalContext.with(
                TERMINAL_ID,
                HAS_USER,
                INPUT,
                OUTPUT,
                null,
                EVALUATOR,
                EXPRESSION_EVALUATION_CONTEXT_FACTORY
            )
        );
    }

    @Test
    public void testWithNullEvaluatorFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicTerminalContext.with(
                TERMINAL_ID,
                HAS_USER,
                INPUT,
                OUTPUT,
                ERROR,
                null,
                EXPRESSION_EVALUATION_CONTEXT_FACTORY
            )
        );
    }

    @Test
    public void testWithNullExpressionEvaluationContextFactoryFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicTerminalContext.with(
                TERMINAL_ID,
                HAS_USER,
                INPUT,
                OUTPUT,
                ERROR,
                EVALUATOR,
                null
            )
        );
    }

    @Override
    public BasicTerminalContext createContext() {
        return BasicTerminalContext.with(
            TERMINAL_ID,
            HAS_USER,
            INPUT,
            OUTPUT,
            ERROR,
            EVALUATOR,
            EXPRESSION_EVALUATION_CONTEXT_FACTORY
        );
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createContext(),
            TERMINAL_ID + ", input: " + INPUT + ", output: " + OUTPUT + ", error: " + ERROR
        );
    }

    // class............................................................................................................

    @Override
    public Class<BasicTerminalContext> type() {
        return BasicTerminalContext.class;
    }
}
