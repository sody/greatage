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

package com.example.app0;

import org.greatage.inject.Key;
import org.greatage.inject.ServiceLocator;
import org.greatage.inject.internal.ServiceLocatorBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestApplication extends Assert {

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
	public void testServiceBinder() {
		final ServiceLocator locator = ServiceLocatorBuilder.createServiceLocator(MockBindModule.class);

		MockTalkService service = locator.getService(Key.get(MockTalkService.class).named("service1"));
		assertNotNull(service);
		assertEquals(service.say(), "hello");

		service = locator.getService(Key.get(MockTalkService.class).named("service2"));
		assertNotNull(service);
		assertEquals(service.say(), "hello");
	}

	@Test
	public void testServiceInterceptor() {
		final ServiceLocator locator = ServiceLocatorBuilder.createServiceLocator(MockInterceptModule.class);

		MockTalkService service = locator.getService(Key.get(MockTalkService.class).named("talkService1"));
		assertNotNull(service);
		assertEquals(service.say(), "hello");

		service = locator.getService(Key.get(MockTalkService.class).named("talkService2"));
		assertNotNull(service);
		assertEquals(service.say(), "deprecated2:deprecated1:[hello]");
	}
}
