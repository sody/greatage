/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.commonlib.components;

import org.apache.tapestry5.dom.Document;
import org.greatage.tapestry.internal.BaseComponentTest;
import org.greatage.tapestry.internal.TestConstants;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Ivan Khalopik
 * @version $Revision: 110 $ $Date: 2010-04-30 13:01:20 +0300 (Пт, 30 апр 2010) $
 */
public class TestFormatter extends BaseComponentTest {
	private Document document;

	@BeforeClass(groups = TestConstants.INTEGRATION_GROUP)
	public void generateDocument() {
		document = generatePage("TestPageForFormatter");
	}

	@Test(dataProvider = "formatterData", groups = TestConstants.INTEGRATION_GROUP)
	public void testFormatter(String containerId, String expected) {
		final String actual = getChildMarkup(document, containerId);
		assertEquals(actual, expected);
	}

	@DataProvider(name = "formatterData")
	public Object[][] getFormatterData() {
		return new Object[][]{
				{"test1", "Hello, world!"},
				{"test2", "Hello, sody!"},
				{"test3", "Hello, %s!"},
				{"test4", "&lt;Hello, world&gt;!"},
				{"test5", "&lt;Hello, sody&gt;!"},
				{"test6", "&lt;Hello, %s&gt;!"},
				{"test7", "<Hello, world>!"},
				{"test8", "<Hello, sody>!"},
				{"test9", "<Hello, %s>!"},
		};
	}
}
