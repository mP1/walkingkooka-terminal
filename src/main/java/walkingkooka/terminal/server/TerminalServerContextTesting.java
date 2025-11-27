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

package walkingkooka.terminal.server;

import org.junit.jupiter.api.Test;
import walkingkooka.ContextTesting;

import static org.junit.jupiter.api.Assertions.assertThrows;

public interface TerminalServerContextTesting<C extends TerminalServerContext> extends ContextTesting<C> {

    // addTerminalContext...............................................................................................

    @Test
    default void testAddTerminalContextWithNullFunctionFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createContext()
                .addTerminalContext(null)
        );
    }

    // createTerminal...................................................................................................

    @Test
    default void testCreateTerminalContextWithNullEnvironmentContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createContext()
                .createTerminalContext(null)
        );
    }

    // terminalContext..................................................................................................

    @Test
    default void testTerminalContextWithNullTerminalIdFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createContext()
                .terminalContext(null)
        );
    }

    // removeTerminal...................................................................................................

    @Test
    default void testRemoveTerminalContextWithNullTerminalIdFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createContext()
                .terminalContext(null)
        );
    }

    // class............................................................................................................

    @Override//
    default String typeNamePrefix() {
        return "";
    }

    @Override//
    default String typeNameSuffix() {
        return TerminalServerContext.class.getSimpleName();
    }
}
