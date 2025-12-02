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

import walkingkooka.Context;
import walkingkooka.terminal.TerminalContext;
import walkingkooka.terminal.TerminalId;

import java.util.Optional;
import java.util.function.Function;

/**
 * A {@link Context} that provides operations to create and manage {@link walkingkooka.terminal.TerminalContext}.
 */
public interface TerminalServerContext extends Context {

    /**
     * Adds the {@link TerminalContext} created by the factory. Note the created {@link TerminalContext} must use the
     * given {@link TerminalId}.
     */
    TerminalContext addTerminalContext(final Function<TerminalId, TerminalContext> terminalContextFactory);

    /**
     * Gets a {@link TerminalContext} given its {@link TerminalId}.
     */
    Optional<TerminalContext> terminalContext(final TerminalId id);

    /**
     * Fetches the requested {@link TerminalContext} or throws a {@link IllegalArgumentException}.
     */
    default TerminalContext terminalContextOrFail(final TerminalId id) {
        return this.terminalContext(id)
            .orElseThrow(() -> new IllegalArgumentException("Missing Terminal: " + id));
    }

    /**
     * Removes an existing {@link TerminalContext}.
     */
    TerminalServerContext removeTerminalContext(final TerminalId id);
}
