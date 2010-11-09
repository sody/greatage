/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import org.greatage.ioc.internal.ServiceLocatorBuilder;
import org.greatage.ioc.mock.MockTalkService;
import org.greatage.ioc.mock.modules.MockBindModule;
import org.greatage.ioc.mock.modules.MockConfigureModule;
import org.greatage.ioc.mock.modules.MockDecorateModule;
import org.greatage.ioc.mock.modules.MockInterceptModule;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestServiceLocator extends Assert {

	@Test
	public void testServiceConfigurator() {
		final ServiceLocator locator = ServiceLocatorBuilder.createServiceLocator(MockConfigureModule.class);
		final MockTalkService service = locator.getService(MockTalkService.class);
		assertNotNull(service);
		assertEquals(service.say(), "hello");
		assertEquals(service.say(), "world");
		assertEquals(service.say(), "!!!");
	}

	@Test
	public void testServiceDecorator() {
		final ServiceLocator locator = ServiceLocatorBuilder.createServiceLocator(MockDecorateModule.class);
		final MockTalkService service = locator.getService(MockTalkService.class);
		assertNotNull(service);
		assertEquals(service.say(), "{[hello]}");
	}

	@Test
	public void testServiceBinder() {
		final ServiceLocator locator = ServiceLocatorBuilder.createServiceLocator(MockBindModule.class);
		MockTalkService service = locator.getService("talkService1", MockTalkService.class);
		assertNotNull(service);
		assertEquals(service.say(), "hello");

		service = locator.getService("talkService2", MockTalkService.class);
		assertNotNull(service);
		assertEquals(service.say(), "hello");
	}

	@Test
	public void testServiceInterceptor() {
		final ServiceLocator locator = ServiceLocatorBuilder.createServiceLocator(MockInterceptModule.class);

		MockTalkService service = locator.getService("talkService1", MockTalkService.class);
		assertNotNull(service);
		assertEquals(service.say(), "hello");

		service = locator.getService("talkService2", MockTalkService.class);
		assertNotNull(service);
		assertEquals(service.say(), "deprecated2:deprecated1:[hello]");
	}
}
