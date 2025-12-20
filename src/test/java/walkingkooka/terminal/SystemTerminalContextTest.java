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
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.environment.EnvironmentContexts;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.text.LineEnding;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class SystemTerminalContextTest implements TerminalContextTesting<SystemTerminalContext> {

    private final TerminalId TERMINAL_ID = TerminalId.parse("1");

    private final static BiFunction<String, TerminalContext, Object> EVALUATOR = (e, c) -> {
        throw new UnsupportedOperationException();
    };

    private final static EnvironmentContext ENVIRONMENT_CONTEXT = EnvironmentContexts.readOnly(
        EnvironmentContexts.map(
            EnvironmentContexts.empty(
                LineEnding.NL,
                Locale.forLanguageTag("en-AU"),
                LocalDateTime::now,
                Optional.of(
                    EmailAddress.parse("user@example.com")
                )
            )
        )
    );

    @Test
    public void testWithNullTerminalIdFails() {
        assertThrows(
            NullPointerException.class,
            () -> SystemTerminalContext.with(
                null,
                EVALUATOR,
                ENVIRONMENT_CONTEXT
            )
        );
    }

    @Test
    public void testWithNullEvaluatorFails() {
        assertThrows(
            NullPointerException.class,
            () -> SystemTerminalContext.with(
                TERMINAL_ID,
                null,
                ENVIRONMENT_CONTEXT
            )
        );
    }

    @Test
    public void testWithNullEnvironmentContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> SystemTerminalContext.with(
                TERMINAL_ID,
                EVALUATOR,
                null
            )
        );
    }

    @Override
    public SystemTerminalContext createContext() {
        return SystemTerminalContext.with(
            TERMINAL_ID,
            EVALUATOR,
            ENVIRONMENT_CONTEXT.cloneEnvironment()
        );
    }

    // class............................................................................................................

    @Override
    public Class<SystemTerminalContext> type() {
        return SystemTerminalContext.class;
    }
}
