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

package walkingkooka.terminal.expression;

import walkingkooka.environment.EnvironmentContext;
import walkingkooka.environment.EnvironmentValueName;
import walkingkooka.environment.EnvironmentWatcher;
import walkingkooka.io.TextReader;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.storage.StoragePath;
import walkingkooka.terminal.TerminalId;
import walkingkooka.text.Indentation;
import walkingkooka.text.LineEnding;
import walkingkooka.text.printer.Printer;
import walkingkooka.tree.expression.FakeExpressionEvaluationContext;

import java.nio.charset.Charset;
import java.time.ZoneOffset;
import java.util.Currency;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

public class FakeTerminalExpressionEvaluationContext extends FakeExpressionEvaluationContext implements TerminalExpressionEvaluationContext {

    public FakeTerminalExpressionEvaluationContext() {
        super();
    }

    @Override
    public TerminalId terminalId() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isTerminalOpen() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void exitTerminal(final Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TextReader input() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Printer output() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Printer error() {
        throw new UnsupportedOperationException();
    }

    // EnvironmentContext...............................................................................................

    @Override
    public TerminalExpressionEvaluationContext cloneEnvironment() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TerminalExpressionEvaluationContext setEnvironmentContext(final EnvironmentContext environmentContext) {
        throw new UnsupportedOperationException();
    }

    @Override
    public EnvironmentValueName<?> parseEnvironmentValueName(final String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> Optional<T> environmentValue(final EnvironmentValueName<T> environmentValueName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> void setEnvironmentValue(final EnvironmentValueName<T> environmentValueName,
                                        final T value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeEnvironmentValue(final EnvironmentValueName<?> environmentValueName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCharset(final Charset charset) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Currency currency() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCurrency(final Currency currency) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<StoragePath> currentWorkingDirectory() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCurrentWorkingDirectory(final Optional<StoragePath> optional) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<StoragePath> homeDirectory() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setHomeDirectory(final Optional<StoragePath> optional) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setIndentation(final Indentation indentation) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLineEnding(final LineEnding lineEnding) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLocale(final Locale locale) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ZoneOffset timeOffset() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setTimeOffset(final ZoneOffset zoneOffset) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<EmailAddress> user() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setUser(final Optional<EmailAddress> optional) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<EnvironmentValueName<?>> environmentValueNames() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Runnable addEnvironmentWatcher(final EnvironmentWatcher environmentWatcher) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Runnable addEnvironmentWatcherOnce(final EnvironmentWatcher environmentWatcher) {
        throw new UnsupportedOperationException();
    }
}
