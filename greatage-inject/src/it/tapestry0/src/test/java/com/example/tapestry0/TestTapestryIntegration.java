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

package com.example.tapestry0;

import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.RegistryBuilder;
import org.greatage.inject.Key;
import org.greatage.inject.Marker;
import org.greatage.inject.ServiceLocator;
import org.greatage.inject.internal.ServiceLocatorBuilder;
import org.greatage.inject.tapestry.GreatAgeIntegration;
import org.greatage.inject.tapestry.TapestryIntegration;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestTapestryIntegration extends Assert {

	@Test
	public void testGreatAgeSide() {
		final ServiceLocator locator = new ServiceLocatorBuilder()
				.addModules(MockGreatAgeModule1.class, MockGreatAgeModule2.class)
				.addModule(new TapestryIntegration(MockTapestryModule1.class))
				.build();
		assertNotNull(locator);

		Marker<MockMessageService> marker = Key.get(MockMessageService.class).named("GreatAgeMessageService");
		MockMessageService messageService = locator.getService(marker);
		assertNotNull(messageService);
		assertNotNull(messageService.generateMessage());
		assertNotNull(messageService.generateMessage());

		marker = Key.get(MockMessageService.class).named("TapestryMessageService");
		messageService = locator.getService(marker);
		assertNotNull(messageService);
		assertNotNull(messageService.generateMessage());
		assertNotNull(messageService.generateMessage());

		marker = Key.get(MockMessageService.class).named("GreatAgeMessageServiceDelegate");
		messageService = locator.getService(marker);
		assertNotNull(messageService);
		assertNotNull(messageService.generateMessage());
		assertTrue(messageService.generateMessage().startsWith("Invocation from greatage:"));
	}

	@Test
	public void testTapestrySide() {
		final Registry registry = RegistryBuilder.buildAndStartupRegistry(
				new GreatAgeIntegration(MockGreatAgeModule1.class),
				MockTapestryModule1.class, MockTapestryModule2.class);
		assertNotNull(registry);

		MockMessageService messageService = registry.getService("GreatAgeMessageService", MockMessageService.class);
		assertNotNull(messageService);
		assertNotNull(messageService.generateMessage());
		assertNotNull(messageService.generateMessage());

		messageService = registry.getService("TapestryMessageService", MockMessageService.class);
		assertNotNull(messageService);
		assertNotNull(messageService.generateMessage());
		assertNotNull(messageService.generateMessage());

		messageService = registry.getService("TapestryMessageServiceDelegate", MockMessageService.class);
		assertNotNull(messageService);
		assertNotNull(messageService.generateMessage());
		assertTrue(messageService.generateMessage().startsWith("Invocation from tapestry:"));
	}
}
