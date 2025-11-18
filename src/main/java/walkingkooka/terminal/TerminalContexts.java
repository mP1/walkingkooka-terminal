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
import walkingkooka.environment.HasUser;
import walkingkooka.reflect.PublicStaticHelper;
import walkingkooka.text.printer.Printer;

import java.util.Optional;
import java.util.function.Function;

public final class TerminalContexts implements PublicStaticHelper {

    /**
     * {@see BasicTerminalContext}
     */
    public static TerminalContext basic(final TerminalId terminalId,
                                        final HasUser hasUser,
                                        final Function<Long, Optional<String>> input,
                                        final Printer printer) {
        return BasicTerminalContext.with(
            terminalId,
            hasUser,
            input,
            printer
        );
    }

    /**
     * {@see FakeTerminalContext}
     */
    public static FakeTerminalContext fake() {
        return new FakeTerminalContext();
    }

    /**
     * {@see PrinterTerminalContext}
     */
    public static TerminalContext printer(final TerminalId terminalId,
                                          final HasUser hasUser,
                                          final Printer printer) {
        return PrinterTerminalContext.with(
            terminalId,
            hasUser,
            printer
        );
    }

    /**
     * {@see SystemTerminalContext}
     */
    @GwtIncompatible
    public static TerminalContext system(final TerminalId terminalId,
                                         final HasUser hasUser) {
        return SystemTerminalContext.with(
            terminalId,
            hasUser
        );
    }

    /**
     * Stop creation
     */
    private TerminalContexts() {
        throw new UnsupportedOperationException();
    }
}
