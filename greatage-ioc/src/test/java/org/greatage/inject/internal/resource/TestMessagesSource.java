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

package org.greatage.inject.internal.resource;

import org.greatage.inject.internal.i18n.MessagesSourceImpl;
import org.greatage.inject.services.Messages;
import org.greatage.inject.services.MessagesSource;
import org.greatage.inject.services.ResourceProvider;
import org.greatage.util.CollectionUtils;
import org.greatage.util.LocaleUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Locale;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestMessagesSource extends Assert {
	private MessagesSource messagesSource;

	@BeforeClass
	public void setupMessagesSource() {
		final ResourceProvider resourceProvider = ClasspathResource.root();
		final ResourceLocatorImpl resourceLocator = new ResourceLocatorImpl(CollectionUtils.newList(resourceProvider));
		messagesSource = new MessagesSourceImpl(resourceLocator);
	}

	@DataProvider
	public Object[][] getMessagesData() {
		return new Object[][]{
				{TestMessagesSource.class, null, CollectionUtils.newMap("test", "Test", "class", "Class")},
				{TestMessagesSource.class, LocaleUtils.ROOT_LOCALE, CollectionUtils.newMap("test", "Test", "class", "Class")},
				{TestMessagesSource.class, Locale.ENGLISH, CollectionUtils.newMap("test", "Test", "class", "Class")},
				{TestMessagesSource.class, new Locale("ru"), CollectionUtils.newMap("test", "Тест", "class", "Класс")},
				{TestMessagesSource.class, new Locale("ru", "RU", "xxx"), CollectionUtils.newMap("test", "Тест", "class", "Класс")},
		};
	}

	@Test(dataProvider = "getMessagesData")
	public void testGetMessages(final Class clazz, final Locale locale, final Map<String, String> expected) {
		final Messages messages = messagesSource.getMessages(clazz, locale);
		assertNotNull(messages);
		for (Map.Entry<String, String> entry : expected.entrySet()) {
			assertEquals(messages.get(entry.getKey()), entry.getValue());
		}
	}

}
