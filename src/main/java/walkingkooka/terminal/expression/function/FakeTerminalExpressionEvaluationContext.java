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

package walkingkooka.terminal.expression.function;

import walkingkooka.text.LineEnding;
import walkingkooka.tree.expression.FakeExpressionEvaluationContext;

import java.util.Optional;

public class FakeTerminalExpressionEvaluationContext extends FakeExpressionEvaluationContext implements TerminalExpressionEvaluationContext {

    public FakeTerminalExpressionEvaluationContext() {
        super();
    }

    @Override
    public boolean isTerminalInteractive() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<String> readLine(final long timeout) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void print(final CharSequence charSequence) {
        throw new UnsupportedOperationException();
    }

    @Override
    public LineEnding lineEnding() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void flush() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException();
    }
}
