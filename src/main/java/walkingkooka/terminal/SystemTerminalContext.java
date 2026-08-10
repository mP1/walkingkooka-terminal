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

import javaemul.internal.annotations.GwtIncompatible;
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.io.TextReader;
import walkingkooka.io.TextReaders;
import walkingkooka.storage.StorageEnvironmentContext;
import walkingkooka.storage.StorageEnvironmentContextDelegator;
import walkingkooka.text.printer.Printer;
import walkingkooka.text.printer.Printers;
import walkingkooka.util.OpenChecker;

import java.io.InputStreamReader;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * A {@link TerminalContext} that reads and write to the System IN and OUT streams.
 */
@GwtIncompatible
final class SystemTerminalContext implements TerminalContext,
    StorageEnvironmentContextDelegator {

    /**
     * Factory that creates a new {@link SystemTerminalContext}.
     */
    static SystemTerminalContext with(final TerminalId terminalId,
                                      final BiFunction<String, TerminalContext, Object> evaluator,
                                      final Consumer<Object> exitValue,
                                      final StorageEnvironmentContext storageEnvironmentContext) {
        return new SystemTerminalContext(
            Objects.requireNonNull(terminalId, "terminalId"),
            Objects.requireNonNull(evaluator, "evaluator"),
            Objects.requireNonNull(exitValue, "exitValue"),
            Objects.requireNonNull(storageEnvironmentContext, "storageEnvironmentContext")
        );
    }

    private SystemTerminalContext(final TerminalId terminalId,
                                  final BiFunction<String, TerminalContext, Object> evaluator,
                                  final Consumer<Object> exitValue,
                                  final StorageEnvironmentContext storageEnvironmentContext) {
        this.terminalId = terminalId;

        this.input = TextReaders.reader(
            new InputStreamReader(System.in),
            (c) -> {
            } // dont echo!
        );
        this.output = Printers.sysOut();
        this.error = Printers.sysErr();

        this.evaluator = evaluator;

        this.exitValue = exitValue;

        this.openChecker = OpenChecker.with(
            "Terminal closed",
            (String message) -> new IllegalStateException(message)
        );

        this.storageEnvironmentContext = storageEnvironmentContext;
    }

    @Override
    public TerminalId terminalId() {
        return this.terminalId;
    }

    private final TerminalId terminalId;

    @Override
    public boolean isTerminalOpen() {
        return false == this.openChecker.isClosed();
    }

    @Override
    public void exitTerminal(final Object exitValue) {
        this.openChecker.close();
        this.exitValue.accept(exitValue);
    }

    private final Consumer<Object> exitValue;

    @Override
    public TextReader input() {
        return this.input;
    }

    private final TextReader input;

    @Override
    public Printer output() {
        return this.output;
    }

    private final Printer output;

    @Override
    public Printer error() {
        return this.error;
    }

    private final Printer error;

    @Override
    public Object evaluate(final String expression) {
        Objects.requireNonNull(expression, "expression");
        this.openChecker.check();

        return this.evaluator.apply(
            expression,
            this
        );
    }

    private final BiFunction<String, TerminalContext, Object> evaluator;

    private final OpenChecker<IllegalStateException> openChecker;

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
            new SystemTerminalContext(
                this.terminalId,
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
        return this.terminalId() + " " + this.storageEnvironmentContext;
    }
}
