/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
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
public class TestPathUtils extends Assert {

	@DataProvider
	public Object[][] matchAntPatternData() {
		return new Object[][]{
				{"abc", "abc", true},
				{"ab", "abc", false},
				{"abc", "ab", false},
				{"ab?", "abc", true},
				{"a?c", "abc", true},
				{"a?c", "acc", true},
				{"a?c", "acb", false},
				{"??c", "abc", true},
				{"??c", "bbc", true},
				{"??c", "bbb", false},
				{"???", "abc", true},
				{"???", "aaa", true},

				{"*", "abc", true},
				{"*", "bc", true},
				{"*", "", true},
				{"*", "a/", true},

				{"a*", "abc", true},
				{"ab*", "abc", true},
				{"abc*", "abc", true},
				{"ab*", "acb", false},
				{"abc*cba", "abccba", true},
				{"abc*cba", "abcba", false},
				{"a*?c", "abc", true},
				{"a*?c", "abbc", true},
				{"a*?c", "ac", false},
		};
	}

	@Test(dataProvider = "matchAntPatternData")
	public void testMatchAntPattern(final String pattern, final String text, final boolean expected) {
		final boolean actual = PathUtils.matchAntPattern(text, pattern);
		assertEquals(actual, expected);
	}

	@Test(dataProvider = "matchAntPatternData")
	public void testSimpleMatchAntPath(final String pattern, final String path, final boolean expected) {
		final boolean actual = PathUtils.matchAntPath(path, pattern);
		assertEquals(actual, expected);
	}

	@DataProvider
	public Object[][] matchAntPathData() {
		return new Object[][]{
				{"/**", "/some-file", true},
				{"/**", "/some-file/", true},
				{"/**", "/some/folder/some-file", true},
				{"/**", "/some/folder/some-file.ext", true},
				{"/**", "some-file", false},
				{"/**", "some-file/", false},
				{"/**", "some/folder/some-file.ext", false},

				{"**", "some-file", true},
				{"**", "some-file/", true},
				{"**", "some/folder/some-file", true},
				{"**", "some/folder/some-file.ext", true},
				{"**", "/some-file", false},
				{"**", "/some/folder/some-file.ext", false},

				{"**/*", "some-file.ext", true},
				{"**/*", "some folder/some_file.ext", true},
				{"**/*", "some/folder/someFile.ext", true},
				{"**/*", "some/folder/someFile", true},
				{"**/*", "some/folder/someFile/", true},
				{"**/*", "some/folder/some/file", true},
				{"**/*", "some/folder/some/file.ext", true},
				{"**/*", "/some-file", false},
				{"**/*", "/some/folder/some-file.ext", false},

				{"**/*.*", "some-file.ext", true},
				{"**/*.*", "some folder/some_file.ext", true},
				{"**/*.*", "some/folder/someFile.ext", true},
				{"**/*.*", "/some/folder/someFile.ext", false},
				{"**/*.*", "some-file", false},
				{"**/*.*", "some/folder/", false},
				{"**/*.*", "some folder/some_file", false},
				{"**/*.*", "some/folder/someFile", false},

				{"some/**/*", "some/folder/someFile.ext", true},
				{"some/**/*", "some/folder/some/file.ext", true},
				{"some/**/*", "/some/folder/some/file.ext", false},
				{"some/**/*", "some/folder/some/file", true},
				{"folder/**/*", "some/folder/someFile.ext", false},
				{"folder/**/*", "some/folder/some/file.ext", false},

				{"some/**/*.*", "some/folder/someFile.ext", true},
				{"some/**/*.*", "some/folder/some/file.ext", true},
				{"some/**/*.*", "/some/folder/some/file.ext", false},
				{"some/**/*.*", "some/folder/some/file", false},
				{"folder/**/*.*", "some/folder/someFile.ext", false},
				{"folder/**/*.*", "some/folder/some/file.ext", false},

				{"some/**/some/*.*", "some/folder/someFile.ext", false},
				{"some/**/some/*.*", "some/folder/some/file.ext", true},
				{"some/**/some/*.*", "some/folder/some/file", false},
				{"some/**/some/*.*", "/some/folder/some/file.ext", false},
				{"some/**/some/*.*", "some/folder/other/folder/some/file.ext", true},
				{"some/**/some/*.*", "some/folder/other/folder/some/file", false},
				{"some/**/some/*.*", "some/folder/some/other/folder/some/file.ext", true},
				{"some/**/some/*.*", "some/folder/some/other/folder/some/file", false},
		};
	}

	@Test(dataProvider = "matchAntPathData")
	public void testMatchAntPath(final String pattern, final String path, final boolean expected) {
		final boolean actual = PathUtils.matchAntPath(path, pattern);
		assertEquals(actual, expected);
	}

	@DataProvider
	public Object[][] normalizePathData() {
		return new Object[][]{
				{"C:\\some\\folder\\some\\file.ext", "C:/some/folder/some/file.ext"},
				{"C:\\some\\folder/some/file.ext", "C:/some/folder/some/file.ext"},
				{"file://C:\\some\\folder\\some\\file.ext", "file://C:/some/folder/some/file.ext"},
				{"file://C:\\some\\folder/some/file.ext", "file://C:/some/folder/some/file.ext"},
				{"file:\\\\C:\\some\\folder\\some\\file.ext", "file://C:/some/folder/some/file.ext"},
		};
	}

	@Test(dataProvider = "normalizePathData")
	public void testNormalizePath(final String path, final String expected) {
		final String actual = PathUtils.normalizePath(path);
		assertEquals(actual, expected);
	}
}
