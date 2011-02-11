package org.greatage.util;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestDescriptionBuilder extends Assert {

	@DataProvider
	public Object[][] descriptionBuilderData() {
		return new Object[][]{
				{new DescriptionBuilder(getClass()), "TestDescriptionBuilder"},
				{new DescriptionBuilder((String) null), "null"},
				{new DescriptionBuilder(getClass()).append("test"), "TestDescriptionBuilder(test)"},
				{new DescriptionBuilder(getClass()).append(null), "TestDescriptionBuilder(null)"},
				{new DescriptionBuilder(getClass()).append(null, null), "TestDescriptionBuilder(null=null)"},
				{new DescriptionBuilder("Test").append("test").append("data"), "Test(test,data)"},
				{new DescriptionBuilder("Test").append("test").append("test", "data"), "Test(test,test=data)"},
				{
						new DescriptionBuilder("Test").append("test").append("test", "data").append("test", "data1"),
						"Test(test,test=data,test=data1)"
				},
		};
	}

	@Test(dataProvider = "descriptionBuilderData")
	public void testDescriptionBuilder(final DescriptionBuilder builder, final String expected) {
		assertEquals(builder.toString(), expected);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void testDescriptionBuilderWrong() {
		new DescriptionBuilder((Class) null);
	}
}
