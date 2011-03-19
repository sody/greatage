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

package org.greatage.ioc.guice;

import org.greatage.ioc.ServiceLocator;
import org.greatage.ioc.ServiceLocatorBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class TestGuiceIntegration extends Assert {

	@Test
	public void testGreatAgeSide() {
		final ServiceLocatorBuilder builder = new ServiceLocatorBuilder();
		builder.addModule(MockGreatAgeModule.class);
		builder.addModule(new GuiceModule(new MockGuiceModule()));
		final ServiceLocator locator = builder.build();
		assertNotNull(locator);

		MockMessageService messageService = locator.getService("GreatAgeMessageService", MockMessageService.class);
		assertNotNull(messageService);
		assertNotNull(messageService.generateMessage());
		assertNotNull(messageService.generateMessage());

		messageService = locator.getService("GuiceMessageService", MockMessageService.class);
		assertNotNull(messageService);
		assertNotNull(messageService.generateMessage());
		assertNotNull(messageService.generateMessage());

		messageService = locator.getService("GreatAgeMessageServiceDelegate", MockMessageService.class);
		assertNotNull(messageService);
		assertNotNull(messageService.generateMessage());
		assertTrue(messageService.generateMessage().startsWith("Invocation from greatage:"));

		messageService = locator.getService("GuiceMessageServiceDelegate", MockMessageService.class);
		assertNotNull(messageService);
		assertNotNull(messageService.generateMessage());
		assertTrue(messageService.generateMessage().startsWith("Invocation from guice:"));
	}
}
