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
import walkingkooka.environment.HasUser;
import walkingkooka.net.email.EmailAddress;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class SystemTerminalContextTest implements TerminalContextTesting<SystemTerminalContext> {

    private final TerminalId TERMINAL_ID = TerminalId.parse("1");

    private final HasUser HAS_USER = () -> Optional.of(
        EmailAddress.parse("user@example.com")
    );

    @Test
    public void testWithNullTerminalIdFails() {
        assertThrows(
            NullPointerException.class,
            () -> SystemTerminalContext.with(
                null,
                HAS_USER
            )
        );
    }

    @Test
    public void testWithNullHasUserFails() {
        assertThrows(
            NullPointerException.class,
            () -> SystemTerminalContext.with(
                TERMINAL_ID,
                null
            )
        );
    }

    @Override
    public SystemTerminalContext createContext() {
        return SystemTerminalContext.with(
            TERMINAL_ID,
            HAS_USER
        );
    }

    // class............................................................................................................

    @Override
    public Class<SystemTerminalContext> type() {
        return SystemTerminalContext.class;
    }
}
