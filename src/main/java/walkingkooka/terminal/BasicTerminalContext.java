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

import walkingkooka.environment.EnvironmentContext;
import walkingkooka.io.TextReader;
import walkingkooka.storage.StorageEnvironmentContext;
import walkingkooka.storage.StorageEnvironmentContextDelegator;
import walkingkooka.text.printer.Printer;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A {@link TerminalContext} that reads line from a {@link Function}, with the timeout, and prints to a {@link Printer}.
 */
final class BasicTerminalContext implements TerminalContext,
    StorageEnvironmentContextDelegator {

    static BasicTerminalContext with(final TerminalId terminalId,
                                     final BooleanSupplier openTester,
                                     final TextReader input,
                                     final Printer output,
                                     final Printer error,
                                     final BiFunction<String, TerminalContext, Object> evaluator,
                                     final Consumer<Object> exitValue,
                                     final StorageEnvironmentContext storageEnvironmentContext) {
        return new BasicTerminalContext(
            Objects.requireNonNull(terminalId, "terminalId"),
            Objects.requireNonNull(openTester, "openTester"),
            Objects.requireNonNull(input, "input"),
            Objects.requireNonNull(output, "output"),
            Objects.requireNonNull(error, "error"),
            Objects.requireNonNull(evaluator, "evaluator"),
            Objects.requireNonNull(exitValue, "exitValue"),
            Objects.requireNonNull(storageEnvironmentContext, "storageEnvironmentContext")
        );
    }

    private BasicTerminalContext(final TerminalId terminalId,
                                 final BooleanSupplier openTester,
                                 final TextReader input,
                                 final Printer output,
                                 final Printer error,
                                 final BiFunction<String, TerminalContext, Object> evaluator,
                                 final Consumer<Object> exitValue,
                                 final StorageEnvironmentContext storageEnvironmentContext) {
        this.terminalId = terminalId;

        this.openTester = openTester;

        this.input = input;
        this.output = output;
        this.error = error;

        this.evaluator = evaluator;

        this.exitValue = exitValue;

        this.storageEnvironmentContext = storageEnvironmentContext;
        storageEnvironmentContext.setEnvironmentValue(
            TERMINAL_ID,
            terminalId
        );
    }

    @Override
    public TerminalId terminalId() {
        return this.terminalId;
    }

    private final TerminalId terminalId;

    @Override
    public TextReader input() {
        this.verifyTerminalOpen();

        return this.input;
    }

    private final TextReader input;

    @Override
    public void exitTerminal(final Object value) {
        this.exitValue.accept(value);
    }

    private final Consumer<Object> exitValue;

    @Override
    public boolean isTerminalOpen() {
        return this.openTester.getAsBoolean();
    }

    private void verifyTerminalOpen() {
        if (false == this.isTerminalOpen()) {
            throw new IllegalStateException("Terminal " + this.terminalId + " is closed");
        }
    }

    private final BooleanSupplier openTester;

    @Override
    public Printer output() {
        this.verifyTerminalOpen();
        
        return this.output;
    }

    private final Printer output;

    @Override
    public Printer error() {
        this.verifyTerminalOpen();
        
        return this.error;
    }

    private final Printer error;

    @Override
    public Object evaluate(final String expression) {
        Objects.requireNonNull(expression, "expression");
        this.verifyTerminalOpen();

        return this.evaluator.apply(
            expression,
            this
        );
    }

    private final BiFunction<String, TerminalContext, Object> evaluator;

    // StorageEnvironmentContextDelegator...............................................................................

    @Override
    public TerminalContext cloneEnvironment() {
        return this.setEnvironmentContext(
            this.storageEnvironmentContext.cloneEnvironment()
        );
    }

    @Override
    public TerminalContext setEnvironmentContext(final EnvironmentContext context) {
        final StorageEnvironmentContext before = this.storageEnvironmentContext;
        final StorageEnvironmentContext after = before.setEnvironmentContext(context);

        return before == after ?
            this :
            new BasicTerminalContext(
                this.terminalId,
                this.openTester,
                this.input,
                this.output,
                this.error,
                this.evaluator,
                this.exitValue,
                Objects.requireNonNull(after, "context")
            );
    }

    @Override
    public StorageEnvironmentContext storageEnvironmentContext() {
        return this.storageEnvironmentContext;
    }

    private final StorageEnvironmentContext storageEnvironmentContext;

    // Object...........................................................................................................

    @Override
    public String toString() {
        return this.terminalId + ", input: " + this.input + ", output: " + this.output + ", error: " + this.error + " " + this.storageEnvironmentContext;
    }
}
