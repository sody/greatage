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

package org.greatage.tapestry.internal;

import org.apache.tapestry5.dom.Document;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.test.PageTester;
import org.greatage.tapestry.services.CommonModule;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

/**
 * @author Ivan Khalopik
 * @version $Revision: 109 $ $Date: 2010-04-30 11:49:41 +0300 (Пт, 30 апр 2010) $
 */
public class BaseComponentTest extends Assert {
	private static PageTester PAGE_TESTER;

	@BeforeSuite(groups = TestConstants.INTEGRATION_GROUP)
	public void setupPageTester() {
		PAGE_TESTER = new PageTester("org.greatage.tapestry.testapp", "testApp", PageTester.DEFAULT_CONTEXT_PATH, CommonModule.class);
		assertNotNull(PAGE_TESTER);
	}

	@AfterSuite(groups = TestConstants.INTEGRATION_GROUP)
	public void cleanupPageTester() {
		PAGE_TESTER = null;
	}

	protected PageTester getPageTester() {
		return PAGE_TESTER;
	}

	protected Document generatePage(String pageName) {
		assertNotNull(PAGE_TESTER);
		final Document document = PAGE_TESTER.renderPage(pageName);
		assertNotNull(document);
		return document;
	}

	protected String getChildMarkup(Document document, String containerId) {
		final Element element = document.getElementById(containerId);
		assertNotNull(element);
		return element.getChildMarkup();
	}

}
