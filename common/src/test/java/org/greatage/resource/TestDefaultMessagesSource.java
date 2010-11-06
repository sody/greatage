package org.greatage.resource;

import org.greatage.util.CollectionUtils;
import org.greatage.util.I18nUtils;
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
public class TestDefaultMessagesSource extends Assert {
	private MessagesSource messagesSource;

	@BeforeClass
	public void setupMessagesSource() {
		messagesSource = new DefaultMessagesSource();
	}

	@DataProvider
	public Object[][] getMessagesData() {
		return new Object[][]{
				{TestDefaultMessagesSource.class, null, CollectionUtils.newMap("test", "Test", "class", "Class")},
				{TestDefaultMessagesSource.class, I18nUtils.ROOT_LOCALE, CollectionUtils.newMap("test", "Test", "class", "Class")},
				{TestDefaultMessagesSource.class, Locale.ENGLISH, CollectionUtils.newMap("test", "Test", "class", "Class")},
				{TestDefaultMessagesSource.class, new Locale("ru"), CollectionUtils.newMap("test", "Тест", "class", "Класс")},
				{TestDefaultMessagesSource.class, new Locale("ru", "RU", "xxx"), CollectionUtils.newMap("test", "Тест", "class", "Класс")},
		};
	}

	@Test(dataProvider = "getMessagesData")
	public void testGetMessages(Class clazz, Locale locale, Map<String, String> expected) {
		final Messages messages = messagesSource.getMessages(clazz, locale);
		assertNotNull(messages);
		for (Map.Entry<String, String> entry : expected.entrySet()) {
			assertEquals(messages.get(entry.getKey()), entry.getValue());
		}
	}

}
