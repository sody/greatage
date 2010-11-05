package org.greatage.util;

import org.greatage.mock.MockClass;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestReflectionUtils extends Assert {

	@DataProvider(name = "newInstanceData")
	public Object[][] getNewInstanceData() {
		return new Object[][]{
				{MockClass.class, new Object[]{}, new MockClass()},
				{MockClass.class, new Object[]{"test"}, new MockClass("test")},
				{MockClass.class, new Object[]{null}, new MockClass(null)},
				{MockClass.class, new Object[]{"test", true}, new MockClass("test", true)},
				{MockClass.class, new Object[]{null, true}, new MockClass(null, true)},
				{MockClass.class, new Object[]{"test1", true, "test2"}, new MockClass("test1", true, (CharSequence) "test2")},
				{MockClass.class, new Object[]{"test1", true, null}, new MockClass("test1", true, (CharSequence) null)},
		};
	}

	@DataProvider(name = "newInstanceExceptionData")
	public Object[][] getNewInstanceExceptionData() {
		return new Object[][]{
				{MockClass.class, new Object[]{true}},
				{MockClass.class, new Object[]{"test", "test"}},
		};
	}

	@DataProvider(name = "findConstructorsData")
	public Object[][] getFindConstructorsData() {
		return new Object[][]{
				{MockClass.class, new Class<?>[] {}, 1},
				{MockClass.class, new Class<?>[] {String.class}, 1},
				{MockClass.class, new Class<?>[] {null}, 1},
				{MockClass.class, new Class<?>[] {Object.class}, 0},

				{MockClass.class, new Class<?>[] {String.class, Boolean.TYPE}, 1},
				{MockClass.class, new Class<?>[] {String.class, Boolean.class}, 1},
				{MockClass.class, new Class<?>[] {String.class, String.class}, 0},
				{MockClass.class, new Class<?>[] {String.class, null}, 1},
				{MockClass.class, new Class<?>[] {null, Boolean.TYPE}, 1},
				{MockClass.class, new Class<?>[] {null, null}, 1},

				{MockClass.class, new Class<?>[] {String.class, Boolean.TYPE, String.class}, 2},
				{MockClass.class, new Class<?>[] {String.class, Boolean.TYPE, CharSequence.class}, 1},
				{MockClass.class, new Class<?>[] {String.class, Boolean.class, null}, 2},
				{MockClass.class, new Class<?>[] {String.class, Boolean.TYPE, Object.class}, 0},

				{MockClass.class, new Class<?>[] {null, null, null, null}, 0},
		};
	}

	@Test(dataProvider = "newInstanceData")
	public <T> void testNewInstance(Class<T> clazz, Object[] parameters, T expected) {
		final T actual = ReflectionUtils.newInstance(clazz, parameters);
		assertEquals(actual, expected);
	}

	@Test(dataProvider = "newInstanceExceptionData", expectedExceptions = RuntimeException.class)
	public <T> void testNewInstanceException(Class<T> clazz, Object[] parameters) {
		ReflectionUtils.newInstance(clazz, parameters);
	}

	@Test
	public void testNewInstanceWithPrimitives() {
		ReflectionUtils.newInstance(MockClass.class, "test", Boolean.TRUE);
		ReflectionUtils.newInstance(MockClass.class, "test", true);
		ReflectionUtils.newInstance(Boolean.class, true);
		ReflectionUtils.newInstance(Byte.class, (byte) 1);
		ReflectionUtils.newInstance(Character.class, 'c');
		ReflectionUtils.newInstance(Double.class, 1.01d);
		ReflectionUtils.newInstance(Float.class, 1.01f);
		ReflectionUtils.newInstance(Integer.class, 1);
		ReflectionUtils.newInstance(Long.class, 1l);
		ReflectionUtils.newInstance(Short.class, (short) 1);
	}

	@Test(dataProvider = "findConstructorsData")
	public <T> void testFindConstructors(Class<T> clazz, Class<?>[] parameterTypes, int expectedConstructorCount) {
		final List<Constructor<T>> list = ReflectionUtils.findConstructors(clazz, parameterTypes);
		assertNotNull(list);
		assertEquals(list.size(), expectedConstructorCount);
	}
}
