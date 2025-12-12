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

import walkingkooka.environment.HasUser;
import walkingkooka.io.TextReader;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.terminal.expression.TerminalExpressionEvaluationContext;
import walkingkooka.text.printer.Printer;
import walkingkooka.util.OpenChecker;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * A {@link TerminalContext} that reads line from a {@link Function}, with the timeout, and prints to a {@link Printer}.
 */
final class BasicTerminalContext implements TerminalContext {

    static BasicTerminalContext with(final TerminalId terminalId,
                                     final HasUser hasUser,
                                     final TextReader input,
                                     final Printer output,
                                     final Printer error,
                                     final Function<TerminalContext, TerminalExpressionEvaluationContext> expressionEvaluationContextFactory) {
        return new BasicTerminalContext(
            Objects.requireNonNull(terminalId, "terminalId"),
            Objects.requireNonNull(hasUser, "hasUser"),
            Objects.requireNonNull(input, "input"),
            Objects.requireNonNull(output, "output"),
            Objects.requireNonNull(error, "error"),
            Objects.requireNonNull(expressionEvaluationContextFactory, "expressionEvaluationContextFactory")
        );
    }

    private BasicTerminalContext(final TerminalId terminalId,
                                 final HasUser hasUser,
                                 final TextReader input,
                                 final Printer output,
                                 final Printer error,
                                 final Function<TerminalContext, TerminalExpressionEvaluationContext> expressionEvaluationContextFactory) {
        this.terminalId = terminalId;
        this.hasUser = hasUser;
        this.input = input;
        this.output = output;
        this.error = error;

        this.openChecker = OpenChecker.with(
            "Terminal closed",
            (String message) -> new IllegalStateException(message)
        );

        this.expressionEvaluationContextFactory = expressionEvaluationContextFactory;
    }

    @Override
    public TerminalId terminalId() {
        return this.terminalId;
    }

    private final TerminalId terminalId;

    @Override
    public Optional<EmailAddress> user() {
        return this.hasUser.user();
    }

    private final HasUser hasUser;

    @Override
    public TextReader input() {
        this.openChecker.check();

        return this.input;
    }

    private final TextReader input;

    @Override
    public TerminalContext exitTerminal() {
        this.openChecker.close();
        return this;
    }

    @Override
    public boolean isTerminalOpen() {
        return false == this.openChecker.isClosed();
    }

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

    private final OpenChecker<IllegalStateException> openChecker;

    @Override
    public TerminalExpressionEvaluationContext terminalExpressionEvaluationContext() {
        if (null == this.terminalExpressionEvaluationContext) {
            this.terminalExpressionEvaluationContext = this.expressionEvaluationContextFactory.apply(this);
        }
        return this.terminalExpressionEvaluationContext;
    }

    private final Function<TerminalContext, TerminalExpressionEvaluationContext> expressionEvaluationContextFactory;

    private TerminalExpressionEvaluationContext terminalExpressionEvaluationContext;

    // Object...........................................................................................................

    @Override
    public String toString() {
        return this.terminalId + ", input: " + this.input + ", output: " + this.output + ", error: " + this.error;
    }
}
