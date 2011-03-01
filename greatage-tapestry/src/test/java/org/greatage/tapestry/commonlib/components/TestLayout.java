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

package org.greatage.tapestry.commonlib.components;

import org.apache.tapestry5.dom.Document;
import org.greatage.tapestry.internal.BaseComponentTest;
import org.greatage.tapestry.internal.TestConstants;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Ivan Khalopik
 * @version $Revision: 198 $ $Date: 2010-07-09 11:07:50 +0300 (Пт, 09 июл 2010) $
 */
public class TestLayout extends BaseComponentTest {
	private static final String STYLESHEET_FROMAT = "<link type=\"text/css\" rel=\"stylesheet\" href=\"/foo/assets/test/%s\"></link>";
	private String document;

	@BeforeClass(groups = TestConstants.INTEGRATION_GROUP)
	public void generateDocument() {
		final Document doc = generatePage("TestPageForLayout");
		document = doc.toString();
		assertNotNull(document);
	}

	@Test(dataProvider = "layoutAssetData", groups = TestConstants.INTEGRATION_GROUP)
	public void testLayoutAsset(String expected) {
		final String fullExpected = String.format(STYLESHEET_FROMAT, "common/components/" + expected);
		assertTrue(document.contains(fullExpected));
	}

	@Test(dataProvider = "layoutSkinData", groups = TestConstants.INTEGRATION_GROUP)
	public void testLayoutSkin(String expected) {
		final String fullExpected = String.format(STYLESHEET_FROMAT, "theme/" + expected);
		assertTrue(document.contains(fullExpected));
	}

	@DataProvider(name = "layoutAssetData")
	public Object[][] getLayoutAssetData() {
		return new Object[][]{
				{"layout.css"},
				{"reset.css"},
				{"base.css"},
				{"fonts.css"},
		};
	}

	@DataProvider(name = "layoutSkinData")
	public Object[][] getLayoutSkinData() {
		return new Object[][]{
				{"white.css"},
		};
	}
}