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
import walkingkooka.text.TextContext;

import static org.junit.jupiter.api.Assertions.assertThrows;

public interface HasTerminalErrorTextTesting2<H extends HasTerminalErrorText> extends HasTerminalErrorTextTesting {

    @Test
    default void testTerminalErrorTextWithNullTextContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createHasTerminalErrorText()
                .terminalErrorText(null)
        );
    }

    default void terminalErrorTextAndCheck(final TextContext context,
                                           final String expected) {
        final H has = this.createHasTerminalErrorText();

        final String actual = has.terminalErrorText(context);

        this.checkNotEquals(
            null,
            actual,
            has::toString
        );

        this.checkEquals(
            expected,
            actual,
            has::toString
        );
    }

    H createHasTerminalErrorText();
}
