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
import walkingkooka.HasNotFoundTextTesting;
import walkingkooka.ToStringTesting;
import walkingkooka.compare.ComparableTesting2;
import walkingkooka.net.HasUrlFragmentTesting;
import walkingkooka.reflect.ClassTesting2;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.test.ParseStringTesting;
import walkingkooka.text.printer.TreePrintableTesting;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeMarshallingTesting;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

public final class TerminalIdTest implements ClassTesting2<TerminalId>,
    ComparableTesting2<TerminalId>,
    HasNotFoundTextTesting,
    HasUrlFragmentTesting,
    JsonNodeMarshallingTesting<TerminalId>,
    ParseStringTesting<TerminalId>,
    TreePrintableTesting,
    ToStringTesting<TerminalId> {

    private final static Long VALUE = 123L;

    @Test
    public void testWith() {
        final TerminalId id = TerminalId.with(VALUE);
        this.checkEquals(VALUE, id.value(), "value");
        this.checkEquals(VALUE, id.id(), "id");
    }

    // HasNotFoundText..................................................................................................

    @Test
    public void testNotFoundText() {
        this.notFoundTextAndCheck(
            TerminalId.with(0x123456),
            "Terminal \"123456\" not found"
        );
    }

    // HasUrlFragment...................................................................................................

    @Test
    public void testUrlFragment() {
        this.urlFragmentAndCheck(
            TerminalId.with(0x123456),
            "123456"
        );
    }

    // ParseString......................................................................................................

    @Test
    public void testParseInvalidFails() {
        this.parseStringFails(
            "XYZ",
            IllegalArgumentException.class
        );
    }

    @Test
    public void testParse() {
        this.parseStringAndCheck(
            "1A",
            TerminalId.with(0x1a)
        );
    }

    @Override
    public TerminalId parseString(final String text) {
        return TerminalId.parse(text);
    }

    @Override
    public Class<? extends RuntimeException> parseStringFailedExpected(final Class<? extends RuntimeException> type) {
        return type;
    }

    @Override
    public RuntimeException parseStringFailedExpected(final RuntimeException cause) {
        return cause;
    }

    // hashCode/equals..................................................................................................

    @Test
    public void testEqualsDifferentTerminalId() {
        this.checkNotEquals(TerminalId.with(999));
    }

    // Compare..........................................................................................................

    @Test
    public void testCompareLess() {
        this.compareToAndCheckLess(TerminalId.with(VALUE * 2));
    }

    @Test
    public void testCompareArraySort() {
        final TerminalId id1 = TerminalId.with(1);
        final TerminalId id2 = TerminalId.with(2);
        final TerminalId id3 = TerminalId.with(3);

        this.compareToArraySortAndCheck(
            id3, id1, id2,
            id1, id2, id3
        );
    }

    @Override
    public TerminalId createComparable() {
        return TerminalId.with(VALUE);
    }

    // JsonNodeMarshallingTesting.......................................................................................

    @Test
    public void testUnmarshallInvalidStringFails() {
        this.unmarshallFails(
            JsonNode.string("123xyz")
        );
    }

    @Test
    public void testUnmarshall() {
        this.unmarshallAndCheck(
            JsonNode.string("1f"),
            TerminalId.with(0x1f)
        );
    }

    @Test
    public void testMarshall() {
        this.marshallAndCheck(
            TerminalId.with(0x1f),
            JsonNode.string("1f")
        );
    }

    @Test
    public void testMarshallJsonNodeUnmarshallRoundtrip() {
        this.marshallRoundTripTwiceAndCheck(
            TerminalId.with(VALUE)
        );
    }

    @Test
    public void testMarshallJsonNodeUnmarshallRoundtrip2() {
        this.marshallRoundTripTwiceAndCheck(
            TerminalId.with(0xabcd)
        );
    }

    @Override
    public TerminalId createJsonNodeMarshallingValue() {
        return this.createObject();
    }

    @Override
    public TerminalId unmarshall(final JsonNode node,
                                 final JsonNodeUnmarshallContext context) {
        return TerminalId.unmarshall(node, context);
    }

    // ToString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(TerminalId.with(VALUE),
            Long.toHexString(VALUE));
    }

    // type.............................................................................................................

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }

    @Override
    public Class<TerminalId> type() {
        return TerminalId.class;
    }

    // TreePrintable....................................................................................................

    @Test
    public void testTreePrint() {
        this.treePrintAndCheck(
            this.createObject(),
            "7b\n"
        );
    }
}
