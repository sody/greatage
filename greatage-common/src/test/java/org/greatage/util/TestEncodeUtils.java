/*
 * Copyright 2011 Ivan Khalopik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.greatage.util;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestEncodeUtils extends Assert {

	@DataProvider
	public Object[][] encodeData() {
		return new Object[][]{
				{"", "MD5", "d41d8cd98f00b204e9800998ecf8427e"},
				{"test", "MD5", "098f6bcd4621d373cade4e832627b4f6"},
				{"admin", "MD5", "21232f297a57a5a743894a0e4a801fc3"},
				{"tester", "MD5", "f5d1278e8109edd94e1e4197e04873b9"},
				{"Hello World!!!", "MD5", "236bf30c70dc03f69175f030afbe38f3"},

				{"", "SHA1", "da39a3ee5e6b4b0d3255bfef95601890afd80709"},
				{"test", "SHA1", "a94a8fe5ccb19ba61c4c0873d391e987982fbbd3"},
				{"admin", "SHA1", "d033e22ae348aeb5660fc2140aec35850c4da997"},
				{"tester", "SHA1", "ab4d8d2a5f480a137067da17100271cd176607a1"},
				{"Hello World!!!", "SHA1", "0aa941b04274ae04dc5a9bd214f7d5214f36e6de"},
		};
	}

	@DataProvider
	public Object[][] encodeWrongData() {
		return new Object[][]{
				{null, "MD5"},
				{new byte[]{}, null},
				{new byte[]{}, "MD"},
		};
	}

	@DataProvider
	public Object[][] encodeBase64Data() {
		return new Object[][]{
				{new byte[]{}, ""},
				{"test".getBytes(), "dGVzdA=="},
				{"admin".getBytes(), "YWRtaW4="},
				{"tester".getBytes(), "dGVzdGVy"},
				{"Hello World!!!".getBytes(), "SGVsbG8gV29ybGQhISE="},
		};
	}

	@Test(dataProvider = "encodeBase64Data")
	public void testEncodeBase64(final byte[] data, final String expected) {
		final String actual = new String(EncodeUtils.encodeBase64(data));
		assertEquals(actual, expected);
	}

	@Test(dataProvider = "encodeData")
	public void testEncode(final String data, final String algorithm, final String expected) {
		final String actual = StringUtils.toHexString(EncodeUtils.encode(data.getBytes(), algorithm));
		assertEquals(actual, expected);
	}

	@Test(dataProvider = "encodeWrongData", expectedExceptions = RuntimeException.class)
	public void testEncodeWrong(final byte[] data, final String algorithm) {
		StringUtils.toHexString(EncodeUtils.encode(data, algorithm));
	}
}
