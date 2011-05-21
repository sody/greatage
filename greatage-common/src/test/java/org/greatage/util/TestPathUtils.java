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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.Set;

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
				{"some/**/some/*.*", "/some/folder/other/folder/some/file.ext", true},
				{"some/**/some/*.*", "/some/folder/other/folder/some/file", false},
				{"some/**/some/*.*", "/some/folder/some/other/folder/some/file.ext", true},
				{"some/**/some/*.*", "/some/folder/some/other/folder/some/file", false},
		};
	}

	@Test(dataProvider = "matchAntPathData")
	public void testMatchAntPath(final String pattern, final String path, final boolean expected) {
		final boolean actual = PathUtils.matchAntPath(path, pattern);
		assertEquals(actual, expected);
	}

	private static final Set<String> SET_EMPTY = CollectionUtils.newSet();
	private static final Set<String> SET_ALL = CollectionUtils.newSet("**");
	private static final Set<String> SET_CONFIGS = CollectionUtils.newSet("config/**/*.xml", "config/**/*.properties");
	private static final Set<String> SET_JAVA = CollectionUtils.newSet("**/*.java");
	private static final Set<String> SET_XML = CollectionUtils.newSet("**/*.xml");

	@DataProvider
	public Object[][] matchAntPathExData() {
		return new Object[][]{
				//test default sets
				{SET_EMPTY, SET_EMPTY, "some.ext", true},
				{SET_EMPTY, SET_EMPTY, "main/some.ext", true},
				{SET_EMPTY, SET_EMPTY, "config/some.ext", true},
				{SET_EMPTY, SET_EMPTY, "main/java/com/example/some.ext", true},
				{SET_EMPTY, SET_EMPTY, "config/java/com/example/some.ext", true},

				{SET_EMPTY, SET_EMPTY, "Some.java", true},
				{SET_EMPTY, SET_EMPTY, "main/Some.java", true},
				{SET_EMPTY, SET_EMPTY, "config/Some.java", true},
				{SET_EMPTY, SET_EMPTY, "main/java/com/example/Some.java", true},
				{SET_EMPTY, SET_EMPTY, "config/java/com/example/Some.java", true},

				{SET_EMPTY, SET_EMPTY, "some.xml", true},
				{SET_EMPTY, SET_EMPTY, "main/some.xml", true},
				{SET_EMPTY, SET_EMPTY, "config/some.xml", true},
				{SET_EMPTY, SET_EMPTY, "main/java/com/example/some.xml", true},
				{SET_EMPTY, SET_EMPTY, "config/java/com/example/some.xml", true},

				{SET_EMPTY, SET_EMPTY, "some.properties", true},
				{SET_EMPTY, SET_EMPTY, "main/some.properties", true},
				{SET_EMPTY, SET_EMPTY, "config/some.properties", true},
				{SET_EMPTY, SET_EMPTY, "main/java/com/example/some.properties", true},
				{SET_EMPTY, SET_EMPTY, "config/java/com/example/some.properties", true},

				//test all include
				{SET_ALL, SET_EMPTY, "some.ext", true},
				{SET_ALL, SET_EMPTY, "main/some.ext", true},
				{SET_ALL, SET_EMPTY, "config/some.ext", true},
				{SET_ALL, SET_EMPTY, "main/java/com/example/some.ext", true},
				{SET_ALL, SET_EMPTY, "config/java/com/example/some.ext", true},

				{SET_ALL, SET_EMPTY, "Some.java", true},
				{SET_ALL, SET_EMPTY, "main/Some.java", true},
				{SET_ALL, SET_EMPTY, "config/Some.java", true},
				{SET_ALL, SET_EMPTY, "main/java/com/example/Some.java", true},
				{SET_ALL, SET_EMPTY, "config/java/com/example/Some.java", true},

				{SET_ALL, SET_EMPTY, "some.xml", true},
				{SET_ALL, SET_EMPTY, "main/some.xml", true},
				{SET_ALL, SET_EMPTY, "config/some.xml", true},
				{SET_ALL, SET_EMPTY, "main/java/com/example/some.xml", true},
				{SET_ALL, SET_EMPTY, "config/java/com/example/some.xml", true},

				{SET_ALL, SET_EMPTY, "some.properties", true},
				{SET_ALL, SET_EMPTY, "main/some.properties", true},
				{SET_ALL, SET_EMPTY, "config/some.properties", true},
				{SET_ALL, SET_EMPTY, "main/java/com/example/some.properties", true},
				{SET_ALL, SET_EMPTY, "config/java/com/example/some.properties", true},

				//test all exclude
				{SET_ALL, SET_ALL, "some.ext", false},
				{SET_ALL, SET_ALL, "main/some.ext", false},
				{SET_ALL, SET_ALL, "config/some.ext", false},
				{SET_ALL, SET_ALL, "main/java/com/example/some.ext", false},
				{SET_ALL, SET_ALL, "config/java/com/example/some.ext", false},

				{SET_ALL, SET_ALL, "Some.java", false},
				{SET_ALL, SET_ALL, "main/Some.java", false},
				{SET_ALL, SET_ALL, "config/Some.java", false},
				{SET_ALL, SET_ALL, "main/java/com/example/Some.java", false},
				{SET_ALL, SET_ALL, "config/java/com/example/Some.java", false},

				{SET_ALL, SET_ALL, "some.xml", false},
				{SET_ALL, SET_ALL, "main/some.xml", false},
				{SET_ALL, SET_ALL, "config/some.xml", false},
				{SET_ALL, SET_ALL, "main/java/com/example/some.xml", false},
				{SET_ALL, SET_ALL, "config/java/com/example/some.xml", false},

				{SET_ALL, SET_ALL, "some.properties", false},
				{SET_ALL, SET_ALL, "main/some.properties", false},
				{SET_ALL, SET_ALL, "config/some.properties", false},
				{SET_ALL, SET_ALL, "main/java/com/example/some.properties", false},
				{SET_ALL, SET_ALL, "config/java/com/example/some.properties", false},

				//test java exclude
				{SET_ALL, SET_JAVA, "some.ext", true},
				{SET_ALL, SET_JAVA, "main/some.ext", true},
				{SET_ALL, SET_JAVA, "config/some.ext", true},
				{SET_ALL, SET_JAVA, "main/java/com/example/some.ext", true},
				{SET_ALL, SET_JAVA, "config/java/com/example/some.ext", true},

				{SET_ALL, SET_JAVA, "Some.java", false},
				{SET_ALL, SET_JAVA, "main/Some.java", false},
				{SET_ALL, SET_JAVA, "config/Some.java", false},
				{SET_ALL, SET_JAVA, "main/java/com/example/Some.java", false},
				{SET_ALL, SET_JAVA, "config/java/com/example/Some.java", false},

				{SET_ALL, SET_JAVA, "some.xml", true},
				{SET_ALL, SET_JAVA, "main/some.xml", true},
				{SET_ALL, SET_JAVA, "config/some.xml", true},
				{SET_ALL, SET_JAVA, "main/java/com/example/some.xml", true},
				{SET_ALL, SET_JAVA, "config/java/com/example/some.xml", true},

				{SET_ALL, SET_JAVA, "some.properties", true},
				{SET_ALL, SET_JAVA, "main/some.properties", true},
				{SET_ALL, SET_JAVA, "config/some.properties", true},
				{SET_ALL, SET_JAVA, "main/java/com/example/some.properties", true},
				{SET_ALL, SET_JAVA, "config/java/com/example/some.properties", true},

				//test include configs
				{SET_CONFIGS, SET_EMPTY, "some.ext", false},
				{SET_CONFIGS, SET_EMPTY, "main/some.ext", false},
				{SET_CONFIGS, SET_EMPTY, "config/some.ext", false},
				{SET_CONFIGS, SET_EMPTY, "main/java/com/example/some.ext", false},
				{SET_CONFIGS, SET_EMPTY, "config/java/com/example/some.ext", false},

				{SET_CONFIGS, SET_EMPTY, "Some.java", false},
				{SET_CONFIGS, SET_EMPTY, "main/Some.java", false},
				{SET_CONFIGS, SET_EMPTY, "config/Some.java", false},
				{SET_CONFIGS, SET_EMPTY, "main/java/com/example/Some.java", false},
				{SET_CONFIGS, SET_EMPTY, "config/java/com/example/Some.java", false},

				{SET_CONFIGS, SET_EMPTY, "some.xml", false},
				{SET_CONFIGS, SET_EMPTY, "main/some.xml", false},
				{SET_CONFIGS, SET_EMPTY, "config/some.xml", true},
				{SET_CONFIGS, SET_EMPTY, "main/java/com/example/some.xml", false},
				{SET_CONFIGS, SET_EMPTY, "config/java/com/example/some.xml", true},

				{SET_CONFIGS, SET_EMPTY, "some.properties", false},
				{SET_CONFIGS, SET_EMPTY, "main/some.properties", false},
				{SET_CONFIGS, SET_EMPTY, "config/some.properties", true},
				{SET_CONFIGS, SET_EMPTY, "main/java/com/example/some.properties", false},
				{SET_CONFIGS, SET_EMPTY, "config/java/com/example/some.properties", true},

				//test include configs exclude xml
				{SET_CONFIGS, SET_XML, "some.ext", false},
				{SET_CONFIGS, SET_XML, "main/some.ext", false},
				{SET_CONFIGS, SET_XML, "config/some.ext", false},
				{SET_CONFIGS, SET_XML, "main/java/com/example/some.ext", false},
				{SET_CONFIGS, SET_XML, "config/java/com/example/some.ext", false},

				{SET_CONFIGS, SET_XML, "Some.java", false},
				{SET_CONFIGS, SET_XML, "main/Some.java", false},
				{SET_CONFIGS, SET_XML, "config/Some.java", false},
				{SET_CONFIGS, SET_XML, "main/java/com/example/Some.java", false},
				{SET_CONFIGS, SET_XML, "config/java/com/example/Some.java", false},

				{SET_CONFIGS, SET_XML, "some.xml", false},
				{SET_CONFIGS, SET_XML, "main/some.xml", false},
				{SET_CONFIGS, SET_XML, "config/some.xml", false},
				{SET_CONFIGS, SET_XML, "main/java/com/example/some.xml", false},
				{SET_CONFIGS, SET_XML, "config/java/com/example/some.xml", false},

				{SET_CONFIGS, SET_XML, "some.properties", false},
				{SET_CONFIGS, SET_XML, "main/some.properties", false},
				{SET_CONFIGS, SET_XML, "config/some.properties", true},
				{SET_CONFIGS, SET_XML, "main/java/com/example/some.properties", false},
				{SET_CONFIGS, SET_XML, "config/java/com/example/some.properties", true},

				//test include xml exclude configs
				{SET_XML, SET_CONFIGS, "some.ext", false},
				{SET_XML, SET_CONFIGS, "main/some.ext", false},
				{SET_XML, SET_CONFIGS, "config/some.ext", false},
				{SET_XML, SET_CONFIGS, "main/java/com/example/some.ext", false},
				{SET_XML, SET_CONFIGS, "config/java/com/example/some.ext", false},

				{SET_XML, SET_CONFIGS, "Some.java", false},
				{SET_XML, SET_CONFIGS, "main/Some.java", false},
				{SET_XML, SET_CONFIGS, "config/Some.java", false},
				{SET_XML, SET_CONFIGS, "main/java/com/example/Some.java", false},
				{SET_XML, SET_CONFIGS, "config/java/com/example/Some.java", false},

				{SET_XML, SET_CONFIGS, "some.xml", true},
				{SET_XML, SET_CONFIGS, "main/some.xml", true},
				{SET_XML, SET_CONFIGS, "config/some.xml", false},
				{SET_XML, SET_CONFIGS, "main/java/com/example/some.xml", true},
				{SET_XML, SET_CONFIGS, "config/java/com/example/some.xml", false},

				{SET_XML, SET_CONFIGS, "some.properties", false},
				{SET_XML, SET_CONFIGS, "main/some.properties", false},
				{SET_XML, SET_CONFIGS, "config/some.properties", false},
				{SET_XML, SET_CONFIGS, "main/java/com/example/some.properties", false},
				{SET_XML, SET_CONFIGS, "config/java/com/example/some.properties", false},
		};
	}


	@Test(dataProvider = "matchAntPathExData")
	public void testMatchAntPathEx(final Set<String> included, final Set<String> excluded,
								   final String path, final boolean expected) {
		final boolean actual = PathUtils.matchAntPath(path, included, excluded);
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

	@DataProvider
	public Object[][] findResourcesData() {
		return new Object[][]{
				{SET_XML, null, new String[]{"config/subconfig/10.xml", "path/3.xml", "testng.xml"}},
				{SET_CONFIGS, null, new String[]{"config/9.properties", "config/subconfig/10.xml"}},
				{SET_XML, SET_CONFIGS, new String[]{"path/3.xml", "testng.xml"}},
				{SET_CONFIGS, SET_XML, new String[]{"config/9.properties"}},
		};
	}

	private String classPathRoot;

	@BeforeClass
	public void setUpClassPathRoot() {
		final URL url = ClassLoader.getSystemResource("");
		if (url != null) {
			classPathRoot = url.getFile();
		}
	}

	@Test(dataProvider = "findResourcesData")
	public void testFindResources(final Set<String> included, final Set<String> excluded, final String[] expected) {
		final Set<String> actual = PathUtils.findResources(classPathRoot, included, excluded);
		assertEquals(actual.size(), expected.length);
		for (String expect : expected) {
			assertTrue(actual.contains(expect));
		}
	}
}
