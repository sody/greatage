package org.greatage.util;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestStringUtils {

	@DataProvider
	public Object[][] isEmptyData() {
		return new Object[][]{
				{null, true},
				{"", true},
				{" ", false},
				{"some string", false},
		};
	}

	@DataProvider
	public Object[][] toHexStringData() {
		return new Object[][]{
				{new byte[0], ""},
				{new byte[]{1, 2, 13, 17, 45, 127}, "01020d112d7f"},
				{new byte[]{0, -1, -14, 17, -128}, "00fff21180"},
		};
	}

	@DataProvider
	public Object[][] toHexStringWrongData() {
		return new Object[][]{
				{null},
		};
	}

	@Test(dataProvider = "isEmptyData")
	public void testIsEmpty(final String test, final boolean expected) {
		final boolean actual = StringUtils.isEmpty(test);
		Assert.assertEquals(actual, expected);
	}

	@Test(dataProvider = "toHexStringData")
	public void testToHexString(final byte[] test, final String expected) {
		final String actual = StringUtils.toHexString(test);
		Assert.assertEquals(actual, expected);
	}

	@Test(dataProvider = "toHexStringWrongData", expectedExceptions = RuntimeException.class)
	public void testToHexString(final byte[] test) {
		StringUtils.toHexString(test);
	}
}
