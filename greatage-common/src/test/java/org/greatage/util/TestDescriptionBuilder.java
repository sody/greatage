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
				{new DescriptionBuilder(getClass()).append("test"), "TestDescriptionBuilder(test)"},
				{new DescriptionBuilder(getClass()).append(null), "TestDescriptionBuilder(null)"},
				{new DescriptionBuilder(getClass()).append(null, null), "TestDescriptionBuilder(null=null)"},
				{new DescriptionBuilder("Test").append("test").append("data"), "Test(test, data)"},
				{new DescriptionBuilder("Test").append("test").append("test", "data"), "Test(test, test=data)"},
				{new DescriptionBuilder("Test").append("test").append("test", "data").append("test", "data1"),"Test(test, test=data, test=data1)"},
		};
	}

	@Test(dataProvider = "descriptionBuilderData")
	public void testDescriptionBuilder(final DescriptionBuilder builder, final String expected) {
		assertEquals(builder.toString(), expected);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void testDescriptionBuilderWrong() {
		new DescriptionBuilder((Class) null).toString();
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void testDescriptionBuilderWrong2() {
		new DescriptionBuilder((String) null).toString();
	}
}
