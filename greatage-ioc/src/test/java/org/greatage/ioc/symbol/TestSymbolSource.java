/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.symbol;

import org.greatage.ioc.symbol.DefaultSymbolProvider;
import org.greatage.ioc.symbol.SymbolProvider;
import org.greatage.ioc.symbol.SymbolSource;
import org.greatage.ioc.symbol.SymbolSourceImpl;
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
				{null},
				{"s4"},
				{"${s4}"},
				{"${s1"},
//				{"H-${s1}-${s3"}, todo: add this test
				{"${s1}-${s4}"},
		};
	}

	@Test(dataProvider = "symbolSourceData")
	public void testSymbolSource(final String expression, final String expected) {
		final String actual = symbolSource.getValue(expression);
		assertEquals(actual, expected);
	}

	@Test(dataProvider = "symbolSourceWrongData", expectedExceptions = RuntimeException.class)
	public void testSymbolSourceWrong(final String expression) {
		symbolSource.getValue(expression);
	}
}
