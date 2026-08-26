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
import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.io.TextReader;
import walkingkooka.io.TextReaders;
import walkingkooka.storage.StorageEnvironmentContext;
import walkingkooka.text.printer.Printer;
import walkingkooka.text.printer.Printers;

import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class TerminalContextBasicTest implements TerminalContextTesting2<TerminalContextBasic>,
    ToStringTesting<TerminalContextBasic> {

    private final static TerminalId TERMINAL_ID = TerminalId.parse("123");

    private final static BooleanSupplier OPEN_TESTER = () -> true;

    private final static TextReader INPUT = TextReaders.fake();

    private final static Printer OUTPUT = Printers.fake();

    private final static Printer ERROR = Printers.fake();

    private final static BiFunction<String, TerminalContext, Object> EVALUATOR = (e, c) -> {
        throw new UnsupportedOperationException();
    };

    private final static Consumer<Object> EXIT_VALUE = (e) -> {
        throw new UnsupportedOperationException();
    };

    @Test
    public void testWithNullTerminalIdFails() {
        assertThrows(
            NullPointerException.class,
            () -> TerminalContextBasic.with(
                null,
                OPEN_TESTER,
                INPUT,
                OUTPUT,
                ERROR,
                EVALUATOR,
                EXIT_VALUE,
                STORAGE_ENVIRONMENT_CONTEXT
            )
        );
    }

    @Test
    public void testWithNullOpenTesterFails() {
        assertThrows(
            NullPointerException.class,
            () -> TerminalContextBasic.with(
                TERMINAL_ID,
                null,
                INPUT,
                OUTPUT,
                ERROR,
                EVALUATOR,
                EXIT_VALUE,
                STORAGE_ENVIRONMENT_CONTEXT
            )
        );
    }

    @Test
    public void testWithNullInputFails() {
        assertThrows(
            NullPointerException.class,
            () -> TerminalContextBasic.with(
                TERMINAL_ID,
                OPEN_TESTER,
                null,
                OUTPUT,
                ERROR,
                EVALUATOR,
                EXIT_VALUE,
                STORAGE_ENVIRONMENT_CONTEXT
            )
        );
    }

    @Test
    public void testWithNullOutputFails() {
        assertThrows(
            NullPointerException.class,
            () -> TerminalContextBasic.with(
                TERMINAL_ID,
                OPEN_TESTER,
                INPUT,
                null,
                ERROR,
                EVALUATOR,
                EXIT_VALUE,
                STORAGE_ENVIRONMENT_CONTEXT
            )
        );
    }

    @Test
    public void testWithNullErrorFails() {
        assertThrows(
            NullPointerException.class,
            () -> TerminalContextBasic.with(
                TERMINAL_ID,
                OPEN_TESTER,
                INPUT,
                OUTPUT,
                null,
                EVALUATOR,
                EXIT_VALUE,
                STORAGE_ENVIRONMENT_CONTEXT
            )
        );
    }

    @Test
    public void testWithNullEvaluatorFails() {
        assertThrows(
            NullPointerException.class,
            () -> TerminalContextBasic.with(
                TERMINAL_ID,
                OPEN_TESTER,
                INPUT,
                OUTPUT,
                ERROR,
                null,
                EXIT_VALUE,
                STORAGE_ENVIRONMENT_CONTEXT
            )
        );
    }

    @Test
    public void testWithNullExitValueFails() {
        assertThrows(
            NullPointerException.class,
            () -> TerminalContextBasic.with(
                TERMINAL_ID,
                OPEN_TESTER,
                INPUT,
                OUTPUT,
                ERROR,
                EVALUATOR,
                null,
                STORAGE_ENVIRONMENT_CONTEXT
            )
        );
    }

    @Test
    public void testWithNullStorageEnvironmentContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> TerminalContextBasic.with(
                TERMINAL_ID,
                OPEN_TESTER,
                INPUT,
                OUTPUT,
                ERROR,
                EVALUATOR,
                EXIT_VALUE,
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

    @Test
    public void testParseEnvironmentValueNameAfterSetEnvironmentValue() {
        final EnvironmentValueName<String> name = EnvironmentValueName.with(
            "magic",
            String.class
        );

        final TerminalContextBasic context = this.createContext();
        this.setEnvironmentValueAndCheck(
            context,
            name,
            "value123"
        );

        this.parseEnvironmentValueNameAndCheck(
            context,
            name
        );
    }

    @Test
    public void testParseEnvironmentValueNameWrappedMissingTerminalId() {
        final StorageEnvironmentContext storageEnvironmentContext = STORAGE_ENVIRONMENT_CONTEXT.cloneEnvironment();

        final TerminalContextBasic terminalContextBasic = TerminalContextBasic.with(
            TERMINAL_ID,
            OPEN_TESTER,
            INPUT,
            OUTPUT,
            ERROR,
            EVALUATOR,
            EXIT_VALUE,
            storageEnvironmentContext
        );

        storageEnvironmentContext.removeEnvironmentValue(TerminalContext.TERMINAL_ID);

        this.environmentValueAndCheck(
            terminalContextBasic,
            TerminalContext.TERMINAL_ID
        );

        this.parseEnvironmentValueNameAndCheck(
            terminalContextBasic,
            TerminalContext.TERMINAL_ID
        );
    }

    @Override
    public TerminalContextBasic createContext() {
        return TerminalContextBasic.with(
            TERMINAL_ID,
            OPEN_TESTER,
            INPUT,
            OUTPUT,
            ERROR,
            EVALUATOR,
            EXIT_VALUE,
            STORAGE_ENVIRONMENT_CONTEXT.cloneEnvironment()
        );
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        final StorageEnvironmentContext storageEnvironmentContext = STORAGE_ENVIRONMENT_CONTEXT.cloneEnvironment();
        storageEnvironmentContext.setEnvironmentValue(
            TerminalContext.TERMINAL_ID,
            TERMINAL_ID
        );

        this.toStringAndCheck(
            this.createContext(),
            TERMINAL_ID + ", input: " + INPUT + ", output: " + OUTPUT + ", error: " + ERROR + " " + storageEnvironmentContext
        );
    }

    // HasEnvironmentContext............................................................................................

    @Test
    @Override
    public void testEnvironmentContext() {
        final StorageEnvironmentContext environmentContext = STORAGE_ENVIRONMENT_CONTEXT.cloneEnvironment();

        this.environmentContextAndCheck(
            TerminalContextBasic.with(
                TERMINAL_ID,
                OPEN_TESTER,
                INPUT,
                OUTPUT,
                ERROR,
                EVALUATOR,
                EXIT_VALUE,
                environmentContext
            ),
            environmentContext
        );
    }

    // class............................................................................................................

    @Override
    public Class<TerminalContextBasic> type() {
        return TerminalContextBasic.class;
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }
}
