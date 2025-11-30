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
import walkingkooka.ContextTesting;
import walkingkooka.environment.HasUserTesting;
import walkingkooka.text.printer.TreePrintableTesting;

public interface TerminalContextTesting<C extends TerminalContext> extends ContextTesting<C>,
    HasUserTesting,
    TreePrintableTesting {

    default void terminalIdAndCheck(final TerminalContext context,
                                    final TerminalId expected) {
        this.checkEquals(
            expected,
            context.terminalId(),
            context::toString
        );
    }

    @Test
    default void testInputNotNull() {
        this.checkNotEquals(
            null,
            this.createContext()
                .input()
        );
    }

    @Test
    default void testOutputNotNull() {
        this.checkNotEquals(
            null,
            this.createContext()
                .output()
        );
    }

    @Test
    default void testErrorNotNull() {
        this.checkNotEquals(
            null,
            this.createContext()
                .error()
        );
    }

    // class............................................................................................................

    @Override//
    default String typeNamePrefix() {
        return "";
    }

    @Override//
    default String typeNameSuffix() {
        return TerminalContext.class.getSimpleName();
    }
}
