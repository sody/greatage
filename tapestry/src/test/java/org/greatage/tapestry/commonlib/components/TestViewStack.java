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
public class TestViewStack extends BaseComponentTest {
	private Document document;

	@BeforeClass(groups = TestConstants.INTEGRATION_GROUP)
	public void generateDocument() {
		document = generatePage("TestPageForViewStack");
	}

	@Test(dataProvider = "viewStackData", groups = TestConstants.INTEGRATION_GROUP)
	public void testBox(String containerId, String expected) {
		final String actual = getChildMarkup(document, containerId);
		assertNotNull(actual);
		assertTrue(actual.contains(expected));
	}

	@DataProvider(name = "viewStackData")
	public Object[][] getViewStackData() {
		return new Object[][]{
				{"test1", ""},
				{"test2", ""},
				{"test3", ""},
				{"test4", "view"},
				{"test5", "add"},
				{"test6", "add"},
				{"test7", "edit"},
				{"test8", "default"},
		};
	}
}