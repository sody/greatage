package org.greatage.ioc;

import org.greatage.ioc.internal.ServiceLocatorBuilder;
import org.greatage.mock.MockBindModule;
import org.greatage.mock.MockConfigureModule;
import org.greatage.mock.MockDecorateModule;
import org.greatage.mock.MockTalkService;
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
}
