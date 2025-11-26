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
import walkingkooka.Cast;
import walkingkooka.environment.EnvironmentContext;
import walkingkooka.environment.EnvironmentContexts;
import walkingkooka.terminal.FakeTerminalContext;
import walkingkooka.terminal.TerminalId;

import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class BasicTerminalServerContextTest implements TerminalServerContextTesting<BasicTerminalServerContext> {

    @Test
    public void testWithNullEnvironmentContextToTerminalContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicTerminalServerContext.with(null)
        );
    }

    @Test
    public void testCreateTerminalWithDuplicateFails() {
        final TerminalId terminalId = TerminalId.with(999);
        final EnvironmentContext environmentContext = EnvironmentContexts.fake();

        final BasicTerminalServerContext context = BasicTerminalServerContext.with(
            (e) -> new FakeTerminalContext() {
                @Override
                public TerminalId terminalId() {
                    return terminalId;
                }
            }
        );

        context.createTerminalContext(environmentContext);

        final IllegalStateException thrown = assertThrows(
            IllegalStateException.class,
            () -> context.createTerminalContext(environmentContext)
        );

        this.checkEquals(
            "TerminalContext created with duplicate TerminalId: " + terminalId,
            thrown.getMessage()
        );
    }

    @Override
    public BasicTerminalServerContext createContext() {
        final AtomicLong next = new AtomicLong();

        return BasicTerminalServerContext.with(
            (environmentContext) ->
                new FakeTerminalContext() {
                    @Override
                    public TerminalId terminalId() {
                        return this.terminalId;
                    }

                    private final TerminalId terminalId = TerminalId.with(
                        next.incrementAndGet()
                    );
                }
        );
    }

    // class............................................................................................................

    @Override
    public Class<BasicTerminalServerContext> type() {
        return Cast.to(BasicTerminalServerContext.class);
    }
}
