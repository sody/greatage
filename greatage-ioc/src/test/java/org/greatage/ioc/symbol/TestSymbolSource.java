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

package org.greatage.ioc.symbol;

import org.greatage.ioc.ApplicationException;
import org.greatage.util.CollectionUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestSymbolSource extends Assert {
	private SymbolSource symbolSource;

	@BeforeClass
	public void setupSymbolSource() {
		final SymbolProvider symbolProvider = new DefaultSymbolProvider(CollectionUtils.<String, String>newMap(
				"s1", "test1",
				"s2", "test2",
				"s3", "test3"
		));
		final List<SymbolProvider> providers = CollectionUtils.newList(symbolProvider);
		symbolSource = new SymbolSourceImpl(providers);
	}

	@DataProvider
	public Object[][] symbolSourceData() {
		return new Object[][]{
				{"s1", "test1"},
				{"${s1}", "test1"},
				{"H-${s1}", "H-test1"},
				{"H-${s1}-${s2}", "H-test1-test2"},
		};
	}

	@DataProvider
	public Object[][] symbolSourceWrongData() {
		return new Object[][]{
//				{null}, TODO: add this test
				{"s4"},
				{"${s4}"},
				{"${s1"},
//				{"H-${s1}-${s3"}, TODO: add this test
				{"${s1}-${s4}"},
		};
	}

	@Test(dataProvider = "symbolSourceData")
	public void testSymbolSource(final String expression, final String expected) {
		final String actual = symbolSource.getValue(expression);
		assertEquals(actual, expected);
	}

	@Test(dataProvider = "symbolSourceWrongData", expectedExceptions = ApplicationException.class)
	public void testSymbolSourceWrong(final String expression) {
		symbolSource.getValue(expression);
	}
}
