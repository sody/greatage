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

package org.greatage.tapestry.commonlib.components;

import org.apache.tapestry5.dom.Document;
import org.greatage.tapestry.internal.BaseComponentTest;
import org.greatage.tapestry.internal.TestConstants;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Ivan Khalopik
 * @version $Revision: 202 $ $Date: 2010-07-13 13:26:39 +0300 (Вт, 13 июл 2010) $
 */
public class TestBox extends BaseComponentTest {
	private Document document;

	@BeforeClass(groups = TestConstants.INTEGRATION_GROUP)
	public void generateDocument() {
		document = generatePage("TestPageForBox");
	}

	@Test(dataProvider = "boxData", groups = TestConstants.INTEGRATION_GROUP)
	public void testBox(String containerId, String expected) {
		final String actual = getChildMarkup(document, containerId);
		assertEquals(actual, expected);
	}

	@DataProvider(name = "boxData")
	public Object[][] getBoxData() {
		return new Object[][]{
				{"test1", "<fieldset class=\"container t-box\"></fieldset>"},
				{"test3", "<fieldset class=\"container t-box foo\"></fieldset>"},
				{"test4", "<fieldset class=\"container t-box foo bar\"></fieldset>"},
				{"test5", "<fieldset style=\"width: 10px;\" class=\"container t-box\"></fieldset>"},
				{"test6", "<fieldset class=\"container t-box\"></fieldset>"},
				{"test7", "<fieldset class=\"container t-box\">text</fieldset>"},
				{"test8", "<fieldset class=\"container t-box\"><legend>test</legend></fieldset>"},
				{"test9", "<fieldset att2=\"2\" att1=\"1\" class=\"container t-box foo bar\"><legend>test</legend>text</fieldset>"},
		};
	}
}