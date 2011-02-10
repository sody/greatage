/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
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
