/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.javassist;

import org.greatage.mock.MockInterface;
import org.greatage.mock.MockInterfaceImpl2;
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
			assertEquals(newClass.getSuperclass(), superClass == null || superClass.isInterface() ? Object.class : superClass);
		}
	}

	@Test(dataProvider = "constructorWrongData", expectedExceptions = RuntimeException.class)
	public <T> void testConstructorWrong(String className, boolean isInterface, Class<T> superClass) {
		final ClassBuilder<T> builder = new ClassBuilder<T>(pool, className, isInterface, superClass);
		builder.build();
	}
}
