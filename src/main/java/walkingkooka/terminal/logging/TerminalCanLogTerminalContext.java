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

package walkingkooka.terminal.logging;

import walkingkooka.logging.CanLog;
import walkingkooka.logging.CanLogDelegator;
import walkingkooka.logging.CanLogs;
import walkingkooka.logging.LoggingLevel;
import walkingkooka.terminal.TerminalContext;

import java.util.Objects;

/**
 * A {@link CanLog} that routes messages to either {@link TerminalContext#output()} or {@link TerminalContext#error()}.
 */
final class TerminalCanLogTerminalContext implements CanLogDelegator {

    static TerminalCanLogTerminalContext with(final TerminalContext context) {
        return new TerminalCanLogTerminalContext(
            Objects.requireNonNull(context, "context")
        );
    }

    private TerminalCanLogTerminalContext(final TerminalContext context) {
        super();
        this.context = context;
    }

    @Override
    public void log(final LoggingLevel loggingLevel,
                    final String message,
                    final Throwable throwable) {
        Objects.requireNonNull(loggingLevel, "loggingLevel");
        Objects.requireNonNull(message, "message");

        final TerminalContext context = this.context;

        CanLogs.printer(
            loggingLevel == LoggingLevel.ERROR ?
                context.error() :
                context.output()
        ).log(
            loggingLevel,
            message,
            throwable
        );
    }

    // CanLogDelegator..................................................................................................

    @Override
    public CanLog canLog() {
        return this.context;
    }

    private final TerminalContext context;

    @Override
    public String toString() {
        return this.context.output() + " " + this.context.error();
    }
}
