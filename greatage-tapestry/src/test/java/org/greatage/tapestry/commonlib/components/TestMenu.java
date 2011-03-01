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
 * todo: make tests more right
 *
 * @author Ivan Khalopik
 * @version $Revision: 202 $ $Date: 2010-07-13 13:26:39 +0300 (Вт, 13 июл 2010) $
 */
public class TestMenu extends BaseComponentTest {
	private Document document;

	@BeforeClass(groups = TestConstants.INTEGRATION_GROUP)
	public void generateDocument() {
		document = generatePage("TestPageForMenu");
	}

	@Test(dataProvider = "menuData", groups = TestConstants.INTEGRATION_GROUP)
	public void testMenu(String containerId, String expected) {
		final String actual = getChildMarkup(document, containerId);
		assertEquals(actual, expected);
	}

	@Test(dataProvider = "menuItemsData", groups = TestConstants.INTEGRATION_GROUP)
	public void testMenuItems(String containerId, String[] items) {
		final String actual = getChildMarkup(document, containerId);
		assertNotNull(actual);
		for (String item : items) {
			assertTrue(actual.contains(item), "Expected item: " + item);
		}
	}

	@DataProvider(name = "menuData")
	public Object[][] getMenuData() {
		return new Object[][]{
				{"test1", "<ul class=\"container t-menu\"></ul>"},
				{"test2", "<ul class=\"container t-menu foo bar\"></ul>"},
		};
	}

	@DataProvider(name = "menuItemsData")
	public Object[][] getMenuItemsData() {
		return new Object[][]{
				{"test3", new String[]{
						"<li><a href=\"/foo/testpageformenu.menu_1:action1\">Save</a></li>",
						"<li><a href=\"/foo/testpageformenu.menu_1:action2\">Cancel</a></li>",
						"<li><a href=\"/foo/testpageformenu.menu_1:action3\">Action3</a></li>"
				}},
				{"test4", new String[]{
						"<li><a href=\"/foo/testpageformenu.menu_2:action1/false\">Save</a></li>",
						"<li><a href=\"/foo/testpageformenu.menu_2:action2/false\">Cancel</a></li>",
						"<li><a href=\"/foo/testpageformenu.menu_2:action3/false\">Action3</a></li>"
				}},
				{"test5", new String[]{
						"<li><span>Save</span></li>",
						"<li><span>Test</span></li>",
						"<li><a href=\"/foo/testpageformenu.menuitem_1:save\">Action3</a></li>",
						"<li><a href=\"/foo/testpageformenu.menuitem_2:save\">Action4</a></li>",
						"<li><a href=\"/foo/testpageformenu\">Action5</a></li>",
						"<li class=\"selected\"><span>Action6</span></li>"
				}},
				{"test6", new String[]{
						"<li>Action1</li>",
						"<li>Action2</li>",
						"<li>Action3</li>",
						"<li>Action4</li>",
						"<li>Action5</li>",
						"<li class=\"selected\">Action6</li>"
				}},
				{"test7", new String[]{
						"<li>Action1Override</li>",
						"<li><a href=\"/foo/testpageformenu.menu_5:action3\">Action3</a></li>",//todo: wrong!
				}},
				{"test8", new String[]{
						"<li><a href=\"/foo/testpageformenu.menu_6:action2/true\">Cancel</a></li>",
						"<li>Action3Override</li>",
						"<li><a href=\"/foo/testpageformenu.menu_6:action6/true\">Action6</a></li>",
				}},
		};
	}
}