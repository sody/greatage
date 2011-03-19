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

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestReflectionUtils extends Assert {

	@DataProvider
	public Object[][] newInstanceData() {
		return new Object[][]{
				{MockClass.class, new Object[]{}, new MockClass()},
				{MockClass.class, new Object[]{"test"}, new MockClass("test")},
				{MockClass.class, new Object[]{null}, new MockClass(null)},
				{MockClass.class, new Object[]{"test", true}, new MockClass("test", true)},
				{MockClass.class, new Object[]{null, true}, new MockClass(null, true)},
				{
						MockClass.class, new Object[]{"test1", true, "test2"},
						new MockClass("test1", true, (CharSequence) "test2")
				},
				{MockClass.class, new Object[]{"test1", true, null}, new MockClass("test1", true, (CharSequence) null)},
		};
	}

	@DataProvider
	public Object[][] newInstanceWrongData() {
		return new Object[][]{
				{MockClass.class, new Object[]{true}},
				{MockClass.class, new Object[]{"test", "test"}},
		};
	}

	@DataProvider
	public Object[][] findConstructorsData() {
		return new Object[][]{
				{MockClass.class, new Class<?>[]{}, 1},
				{MockClass.class, new Class<?>[]{String.class}, 1},
				{MockClass.class, new Class<?>[]{null}, 1},
				{MockClass.class, new Class<?>[]{Object.class}, 0},

				{MockClass.class, new Class<?>[]{String.class, Boolean.TYPE}, 1},
				{MockClass.class, new Class<?>[]{String.class, Boolean.class}, 1},
				{MockClass.class, new Class<?>[]{String.class, String.class}, 0},
				{MockClass.class, new Class<?>[]{String.class, null}, 1},
				{MockClass.class, new Class<?>[]{null, Boolean.TYPE}, 1},
				{MockClass.class, new Class<?>[]{null, null}, 1},

				{MockClass.class, new Class<?>[]{String.class, Boolean.TYPE, String.class}, 2},
				{MockClass.class, new Class<?>[]{String.class, Boolean.TYPE, CharSequence.class}, 1},
				{MockClass.class, new Class<?>[]{String.class, Boolean.class, null}, 2},
				{MockClass.class, new Class<?>[]{String.class, Boolean.TYPE, Object.class}, 0},

				{MockClass.class, new Class<?>[]{null, null, null, null}, 0},
		};
	}

	@DataProvider
	public Object[][] findExceptionData() {
		final IOException ioException = new IOException();
		final IllegalArgumentException argumentException = new IllegalArgumentException(ioException);
		final Throwable throwable = new Throwable(argumentException);
		final RuntimeException runtimeException = new RuntimeException(throwable);
		return new Object[][]{
				{null, Throwable.class, null},
				{runtimeException, Throwable.class, runtimeException},
				{runtimeException, IOException.class, ioException},
				{runtimeException, RuntimeException.class, runtimeException},
				{runtimeException, IllegalArgumentException.class, argumentException},
				{throwable, RuntimeException.class, argumentException},
				{ioException, RuntimeException.class, null}
		};
	}

	@Test(dataProvider = "newInstanceData")
	public <T> void testNewInstance(final Class<T> clazz, final Object[] parameters, final T expected) {
		final T actual = ReflectionUtils.newInstance(clazz, parameters);
		assertEquals(actual, expected);
	}

	@Test(dataProvider = "newInstanceWrongData", expectedExceptions = RuntimeException.class)
	public <T> void testNewInstanceException(final Class<T> clazz, final Object[] parameters) {
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
	public <T> void testFindConstructors(final Class<T> clazz, final Class<?>[] parameterTypes,
										 final int expectedConstructorCount) {
		final List<Constructor<T>> list = ReflectionUtils.findConstructors(clazz, parameterTypes);
		assertNotNull(list);
		assertEquals(list.size(), expectedConstructorCount);
	}

	@Test(dataProvider = "findExceptionData")
	public <T extends Throwable> void testFindException(final Throwable exception, final Class<T> exceptionClass,
														final T expected) {
		final T actual = ReflectionUtils.findException(exception, exceptionClass);
		assertEquals(actual, expected);
	}
}
