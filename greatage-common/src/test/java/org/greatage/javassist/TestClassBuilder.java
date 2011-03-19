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

package org.greatage.javassist;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestClassBuilder extends Assert {
	private ClassPoolEx pool;

	@BeforeClass
	public void setupClassPool() {
		pool = new ClassPoolEx();
	}

	@DataProvider
	public Object[][] constructorData() {
		return new Object[][]{
				{"TestClass_0", false, null},
				{"TestClass_1", false, Object.class},
				{"TestClass_2", false, MockInterfaceImpl2.class},
				{"TestClass_3", false, MockInterface.class},

				{"TestInterface_0", true, null},
				{"TestInterface_1", true, MockInterface.class},
		};
	}

	@DataProvider
	public Object[][] constructorWrongData() {
		return new Object[][]{
				{"TestInterface_0", true, Object.class},
				{"TestInterface_0", false, Object.class},
		};
	}

	@Test(dataProvider = "constructorData")
	public <T> void testConstructor(String className, boolean isInterface, Class<T> superClass) {
		final ClassBuilder<T> builder = new ClassBuilder<T>(pool, className, isInterface, superClass);
		final Class<T> newClass = builder.build();
		assertNotNull(newClass);
		assertEquals(newClass.getName(), className);
		assertEquals(newClass.isInterface(), isInterface);
		if (superClass != null) {
			assertTrue(superClass.isAssignableFrom(newClass));
		}
		if (!isInterface) {
			assertEquals(newClass.getSuperclass(),
					superClass == null || superClass.isInterface() ? Object.class : superClass);
		}
	}

	@Test(dataProvider = "constructorWrongData", expectedExceptions = RuntimeException.class)
	public <T> void testConstructorWrong(String className, boolean isInterface, Class<T> superClass) {
		final ClassBuilder<T> builder = new ClassBuilder<T>(pool, className, isInterface, superClass);
		builder.build();
	}
}
