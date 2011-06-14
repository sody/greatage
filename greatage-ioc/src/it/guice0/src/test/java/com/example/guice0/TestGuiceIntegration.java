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

package com.example.guice0;

import com.example.guice0.MockGreatAgeModule1;
import com.example.guice0.MockGreatAgeModule2;
import com.example.guice0.MockGuiceModule1;
import com.example.guice0.MockGuiceModule2;
import com.example.guice0.MockMessageService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import org.greatage.ioc.Marker;
import org.greatage.ioc.ServiceLocator;
import org.greatage.ioc.ServiceLocatorBuilder;
import org.greatage.ioc.guice.GreatAgeIntegration;
import org.greatage.ioc.guice.GuiceIntegration;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestGuiceIntegration extends Assert {

	@Test
	public void testGreatAgeSide() {
		final ServiceLocator locator = new ServiceLocatorBuilder()
				.addModules(MockGreatAgeModule1.class, MockGreatAgeModule2.class)
				.addModule(new GuiceIntegration(new MockGuiceModule1()))
				.build();
		assertNotNull(locator);

		Marker<MockMessageService> marker = Marker.get(MockMessageService.class, "GreatAgeMessageService");
		MockMessageService messageService = locator.getService(marker);
		assertNotNull(messageService);
		assertNotNull(messageService.generateMessage());
		assertNotNull(messageService.generateMessage());

		marker = Marker.get(MockMessageService.class, "GuiceMessageService");
		messageService = locator.getService(marker);
		assertNotNull(messageService);
		assertNotNull(messageService.generateMessage());
		assertNotNull(messageService.generateMessage());

		marker = Marker.get(MockMessageService.class, "GreatAgeMessageServiceDelegate");
		messageService = locator.getService(marker);
		assertNotNull(messageService);
		assertNotNull(messageService.generateMessage());
		assertTrue(messageService.generateMessage().startsWith("Invocation from greatage:"));
	}

	@Test
	public void testGuiceSide() {
		final Injector injector = Guice.createInjector(new MockGuiceModule1(), new MockGuiceModule2(),
				new GreatAgeIntegration(MockGreatAgeModule1.class));
		assertNotNull(injector);

		Key<MockMessageService> key = Key.get(MockMessageService.class, Names.named("GreatAgeMessageService"));
		MockMessageService messageService = injector.getInstance(key);
		assertNotNull(messageService);
		assertNotNull(messageService.generateMessage());
		assertNotNull(messageService.generateMessage());

		key = Key.get(MockMessageService.class, Names.named("GuiceMessageService"));
		messageService = injector.getInstance(key);
		assertNotNull(messageService);
		assertNotNull(messageService.generateMessage());
		assertNotNull(messageService.generateMessage());

		key = Key.get(MockMessageService.class, Names.named("GuiceMessageServiceDelegate"));
		messageService = injector.getInstance(key);
		assertNotNull(messageService);
		assertNotNull(messageService.generateMessage());
		assertTrue(messageService.generateMessage().startsWith("Invocation from guice:"));
	}
}
