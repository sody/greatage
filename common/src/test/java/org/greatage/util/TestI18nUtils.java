package org.greatage.util;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Locale;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestI18nUtils extends Assert {

	@DataProvider
	public Object[][] getLocaleData() {
		return new Object[][]{
				{null, null},
				{"", I18nUtils.ROOT_LOCALE},
				{"en", Locale.ENGLISH},
				{"en_US", Locale.US},
				{"en_GB", Locale.UK},
				{"ru", new Locale("ru")},
				{"ru_RU_xxx", new Locale("ru", "RU", "xxx")},
		};
	}

	@Test(dataProvider = "getLocaleData")
	public void testGetLocale(String locale, Locale expected) {
		final Locale actual = I18nUtils.getLocale(locale);
		assertEquals(actual, expected);
	}

	@DataProvider
	public Object[][] getCandidateLocalesData() {
		return new Object[][]{
				{null, CollectionUtils.newList(I18nUtils.ROOT_LOCALE)},
				{I18nUtils.ROOT_LOCALE, CollectionUtils.newList(I18nUtils.ROOT_LOCALE)},
				{Locale.ENGLISH, CollectionUtils.newList(Locale.ENGLISH, I18nUtils.ROOT_LOCALE)},
				{Locale.US, CollectionUtils.newList(Locale.US, Locale.ENGLISH, I18nUtils.ROOT_LOCALE)},
				{new Locale("en", "US", "xxx"), CollectionUtils.newList(new Locale("en", "US", "xxx"), Locale.US, Locale.ENGLISH, I18nUtils.ROOT_LOCALE)},
		};
	}

	@Test(dataProvider = "getCandidateLocalesData")
	public void testGetCandidateLocales(Locale locale, List<Locale> expected) {
		final List<Locale> actual = I18nUtils.getCandidateLocales(locale);
		assertEquals(actual, expected);
	}

}
