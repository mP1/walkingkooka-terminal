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
import walkingkooka.environment.EnvironmentContextDelegator;
import walkingkooka.io.TextReader;
import walkingkooka.io.TextReaders;
import walkingkooka.text.printer.Printer;
import walkingkooka.text.printer.Printers;
import walkingkooka.util.OpenChecker;

import java.io.InputStreamReader;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * A {@link TerminalContext} that reads and write to the System IN and OUT streams.
 */
@GwtIncompatible
final class SystemTerminalContext implements TerminalContext,
    EnvironmentContextDelegator {

    /**
     * Factory that creates a new {@link SystemTerminalContext}.
     */
    static SystemTerminalContext with(final TerminalId terminalId,
                                      final BiFunction<String, TerminalContext, Object> evaluator,
                                      final EnvironmentContext environmentContext) {
        return new SystemTerminalContext(
            Objects.requireNonNull(terminalId, "terminalId"),
            Objects.requireNonNull(evaluator, "evaluator"),
            Objects.requireNonNull(environmentContext, "environmentContext")
        );
    }

    private SystemTerminalContext(final TerminalId terminalId,
                                  final BiFunction<String, TerminalContext, Object> evaluator,
                                  final EnvironmentContext environmentContext) {
        this.terminalId = terminalId;

        this.input = TextReaders.reader(
            new InputStreamReader(System.in),
            (c) -> {
            } // dont echo!
        );
        this.output = Printers.sysOut();
        this.error = Printers.sysErr();

        this.evaluator = evaluator;

        this.openChecker = OpenChecker.with(
            "Terminal closed",
            (String message) -> new IllegalStateException(message)
        );

        this.environmentContext = environmentContext;
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
    public void exitTerminal() {
        this.openChecker.close();
    }

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

    // EnvironmentContextDelegator......................................................................................

    @Override
    public TerminalContext cloneEnvironment() {
        return this.setEnvironmentContext(
            this.environmentContext.cloneEnvironment()
        );
    }

    @Override
    public TerminalContext setEnvironmentContext(final EnvironmentContext context) {
        final EnvironmentContext before = this.environmentContext;

        return before == context ?
            this :
            new SystemTerminalContext(
                this.terminalId,
                this.evaluator,
                Objects.requireNonNull(context, "context")
            );
    }

    @Override
    public EnvironmentContext environmentContext() {
        return this.environmentContext;
    }

    private final EnvironmentContext environmentContext;

    // Object...........................................................................................................

    @Override
    public String toString() {
        return this.terminalId() + " " + this.environmentContext;
    }
}
