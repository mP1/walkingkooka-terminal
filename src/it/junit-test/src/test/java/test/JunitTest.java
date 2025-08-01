/*
 * Copyright © 2020 Miroslav Pokorny
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
 */
package test;


import com.google.j2cl.junit.apt.J2clTestInput;
import org.junit.Assert;
import org.junit.Test;

import walkingkooka.Binary;
import walkingkooka.Either;
import walkingkooka.collect.Range;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.map.Maps;
import walkingkooka.net.AbsoluteUrl;
import walkingkooka.net.HostAddress;
import walkingkooka.net.RelativeUrl;
import walkingkooka.net.Url;
import walkingkooka.net.UrlFragment;
import walkingkooka.net.UrlParameterName;
import walkingkooka.net.UrlPath;
import walkingkooka.net.UrlQueryString;
import walkingkooka.net.UrlScheme;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.net.header.Accept;
import walkingkooka.net.header.AcceptCharset;
import walkingkooka.net.header.AcceptEncoding;
import walkingkooka.net.header.AcceptEncodingValue;
import walkingkooka.net.header.AcceptEncodingValueParameterName;
import walkingkooka.net.header.AcceptLanguage;
import walkingkooka.net.header.CacheControl;
import walkingkooka.net.header.CharsetName;
import walkingkooka.net.header.ContentLanguage;
import walkingkooka.net.header.ContentRange;
import walkingkooka.net.header.Cookie;
import walkingkooka.net.header.ETag;
import walkingkooka.net.header.ETagValidator;
import walkingkooka.net.header.EncodedText;
import walkingkooka.net.header.Encoding;
import walkingkooka.net.header.HttpHeaderName;
import walkingkooka.net.header.IfRange;
import walkingkooka.net.header.LanguageName;
import walkingkooka.net.header.Link;
import walkingkooka.net.header.MediaType;
import walkingkooka.net.header.RangeHeader;
import walkingkooka.net.header.RangeHeaderUnit;
import walkingkooka.net.http.HttpEntity;
import walkingkooka.net.http.HttpMethod;
import walkingkooka.net.http.HttpProtocolVersion;
import walkingkooka.net.http.HttpStatusCode;
import walkingkooka.net.http.HttpTransport;
import walkingkooka.net.http.server.FakeHttpRequest;
import walkingkooka.net.http.server.HttpHandlers;
import walkingkooka.net.http.server.HttpRequest;
import walkingkooka.net.http.server.HttpRequests;
import walkingkooka.net.http.server.HttpResponse;
import walkingkooka.net.http.server.HttpResponses;
import walkingkooka.net.http.server.WebFile;
import walkingkooka.net.http.server.WebFileException;
import walkingkooka.net.http.server.WebFiles;

import java.nio.charset.StandardCharsets;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@J2clTestInput(JunitTest.class)
public class JunitTest {

    @Test
    public void testAssertEquals() {
        Assert.assertEquals(
                1,
                1
        );
    }
}
