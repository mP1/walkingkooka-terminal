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

import walkingkooka.environment.EnvironmentContext;
import walkingkooka.reflect.PublicStaticHelper;
import walkingkooka.terminal.TerminalContext;

import java.util.function.Function;

public final class TerminalServerContexts implements PublicStaticHelper {

    /**
     * {@see BasicTerminalServerContext}
     */
    public static <T extends TerminalContext> TerminalServerContext<T> basic(final Function<EnvironmentContext, T> environmentContextToTerminalContext) {
        return BasicTerminalServerContext.with(environmentContextToTerminalContext);
    }

    /**
     * {@see FakeTerminalServerContext}
     */
    public static <T extends TerminalContext> FakeTerminalServerContext<T> fake() {
        return new FakeTerminalServerContext<>();
    }

    /**
     * Stop creation
     */
    private TerminalServerContexts() {
        throw new UnsupportedOperationException();
    }
}
