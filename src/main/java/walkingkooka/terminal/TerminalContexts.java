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
import walkingkooka.reflect.PublicStaticHelper;
import walkingkooka.text.printer.Printer;

import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public final class TerminalContexts implements PublicStaticHelper {

    /**
     * {@see BasicTerminalContext}
     */
    public static TerminalContext basic(final TerminalId terminalId,
                                        final BooleanSupplier openTester,
                                        final TextReader input,
                                        final Printer output,
                                        final Printer error,
                                        final BiFunction<String, TerminalContext, Object> evaluator,
                                        final Consumer<Object> exitValue,
                                        final EnvironmentContext environmentContext) {
        return BasicTerminalContext.with(
            terminalId,
            openTester,
            input,
            output,
            error,
            evaluator,
            exitValue,
            environmentContext
        );
    }

    /**
     * {@see FakeTerminalContext}
     */
    public static FakeTerminalContext fake() {
        return new FakeTerminalContext();
    }

    /**
     * {@see SystemTerminalContext}
     */
    @GwtIncompatible
    public static TerminalContext system(final TerminalId terminalId,
                                         final BiFunction<String, TerminalContext, Object> evaluator,
                                         final Consumer<Object> exitValue,
                                         final EnvironmentContext environmentContext) {
        return SystemTerminalContext.with(
            terminalId,
            evaluator,
            exitValue,
            environmentContext
        );
    }

    /**
     * Stop creation
     */
    private TerminalContexts() {
        throw new UnsupportedOperationException();
    }
}
