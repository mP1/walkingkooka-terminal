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
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.environment.EnvironmentContexts;
import walkingkooka.io.TextReader;
import walkingkooka.io.TextReaders;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.predicate.Predicates;
import walkingkooka.text.LineEnding;
import walkingkooka.text.printer.Printer;
import walkingkooka.text.printer.Printers;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class BasicTerminalContextTest implements TerminalContextTesting<BasicTerminalContext>,
    ToStringTesting<BasicTerminalContext> {

    private final static TerminalId TERMINAL_ID = TerminalId.parse("123");

    private final static Optional<EmailAddress> USER = Optional.of(
        EmailAddress.parse("user@example.com")
    );

    private final static BooleanSupplier OPEN_TESTER = () -> true;

    private final static Runnable CLOSER = () -> {
    };

    private final static TextReader INPUT = TextReaders.fake();

    private final static Printer OUTPUT = Printers.fake();

    private final static Printer ERROR = Printers.fake();

    private final static BiFunction<String, TerminalContext, Object> EVALUATOR = (e, c) -> {
        throw new UnsupportedOperationException();
    };

    private final static EnvironmentContext ENVIRONMENT_CONTEXT = EnvironmentContexts.readOnly(
        Predicates.always(), // all values are readonly
        EnvironmentContexts.map(
            EnvironmentContexts.empty(
                LineEnding.NL,
                Locale.forLanguageTag("en-AU"),
                () -> LocalDateTime.MIN,
                USER
            )
        )
    );

    @Test
    public void testWithNullTerminalIdFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicTerminalContext.with(
                null,
                OPEN_TESTER,
                CLOSER,
                INPUT,
                OUTPUT,
                ERROR,
                EVALUATOR,
                ENVIRONMENT_CONTEXT
            )
        );
    }

    @Test
    public void testWithNullOpenTesterFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicTerminalContext.with(
                TERMINAL_ID,
                null,
                CLOSER,
                INPUT,
                OUTPUT,
                ERROR,
                EVALUATOR,
                ENVIRONMENT_CONTEXT
            )
        );
    }

    @Test
    public void testWithNullCloserFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicTerminalContext.with(
                TERMINAL_ID,
                OPEN_TESTER,
                null,
                INPUT,
                OUTPUT,
                ERROR,
                EVALUATOR,
                ENVIRONMENT_CONTEXT
            )
        );
    }

    @Test
    public void testWithNullInputFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicTerminalContext.with(
                TERMINAL_ID,
                OPEN_TESTER,
                CLOSER,
                null,
                OUTPUT,
                ERROR,
                EVALUATOR,
                ENVIRONMENT_CONTEXT
            )
        );
    }

    @Test
    public void testWithNullOutputFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicTerminalContext.with(
                TERMINAL_ID,
                OPEN_TESTER,
                CLOSER,
                INPUT,
                null,
                ERROR,
                EVALUATOR,
                ENVIRONMENT_CONTEXT
            )
        );
    }

    @Test
    public void testWithNullErrorFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicTerminalContext.with(
                TERMINAL_ID,
                OPEN_TESTER,
                CLOSER,
                INPUT,
                OUTPUT,
                null,
                EVALUATOR,
                ENVIRONMENT_CONTEXT
            )
        );
    }

    @Test
    public void testWithNullEvaluatorFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicTerminalContext.with(
                TERMINAL_ID,
                OPEN_TESTER,
                CLOSER,
                INPUT,
                OUTPUT,
                ERROR,
                null,
                ENVIRONMENT_CONTEXT
            )
        );
    }

    @Test
    public void testWithNullEnvironmentContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicTerminalContext.with(
                TERMINAL_ID,
                OPEN_TESTER,
                CLOSER,
                INPUT,
                OUTPUT,
                ERROR,
                EVALUATOR,
                null
            )
        );
    }

    @Test
    public void testEnvironmentValueNameTerminalId() {
        this.environmentValueAndCheck(
            this.createContext(),
            TerminalContext.TERMINAL_ID,
            TERMINAL_ID
        );
    }

    @Override
    public BasicTerminalContext createContext() {
        return BasicTerminalContext.with(
            TERMINAL_ID,
            OPEN_TESTER,
            CLOSER,
            INPUT,
            OUTPUT,
            ERROR,
            EVALUATOR,
            ENVIRONMENT_CONTEXT.cloneEnvironment()
        );
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        final EnvironmentContext environmentContext = ENVIRONMENT_CONTEXT.cloneEnvironment();
        environmentContext.setEnvironmentValue(
            TerminalContext.TERMINAL_ID,
            TERMINAL_ID
        );

        this.toStringAndCheck(
            this.createContext(),
            TERMINAL_ID + ", input: " + INPUT + ", output: " + OUTPUT + ", error: " + ERROR + " " + environmentContext
        );
    }

    // class............................................................................................................

    @Override
    public Class<BasicTerminalContext> type() {
        return BasicTerminalContext.class;
    }
}
