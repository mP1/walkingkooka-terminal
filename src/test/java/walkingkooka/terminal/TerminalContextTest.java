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
import walkingkooka.environment.CanParseEnvironmentValueNameTesting;
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.reflect.ClassTesting;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.reflect.ThrowableTesting;
import walkingkooka.storage.StorageEnvironmentContext;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class TerminalContextTest implements CanParseEnvironmentValueNameTesting,
    ClassTesting<TerminalContext>,
    ThrowableTesting {

    // TERMINAL_CONTEXT_PARSE...........................................................................................

    @Test
    public void testTerminalContextParseWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> TerminalContext.TERMINAL_CONTEXT_PARSE.parseEnvironmentValueName(null)
        );
    }

    @Test
    public void testTerminalContextParseWithUnknownFails() {
        final IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> TerminalContext.TERMINAL_CONTEXT_PARSE.parseEnvironmentValueName("unknown")
        );

        this.getMessageAndCheck(
            thrown,
            "Unknown environment value name \"unknown\""
        );
    }

    @Test
    public void testTerminalContextParseWithCharset() {
        this.parseEnvironmentValueNameAndCheck(
            TerminalContext.TERMINAL_CONTEXT_PARSE,
            EnvironmentValueName.CHARSET
        );
    }

    @Test
    public void testTerminalContextParseWithHomeDirectory() {
        this.parseEnvironmentValueNameAndCheck(
            TerminalContext.TERMINAL_CONTEXT_PARSE,
            StorageEnvironmentContext.HOME_DIRECTORY
        );
    }

    @Test
    public void testTerminalContextParseWithEnvironmentConstants() throws Exception {
        int i = 0;

        for (final Field field : EnvironmentContext.class.getDeclaredFields()) {
            if (field.getType() == EnvironmentValueName.class) {
                this.parseEnvironmentValueNameAndCheck(
                    TerminalContext.TERMINAL_CONTEXT_PARSE,
                    (EnvironmentValueName<?>) field.get(null)
                );
                i++;
            }
        }

        this.checkNotEquals(
            0,
            i
        );
    }
    
    // class............................................................................................................

    @Override
    public Class<TerminalContext> type() {
        return TerminalContext.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
