/*
 * Copyright 2011 Ivan Khalopik
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

package org.greatage.ioc.coerce;

import org.greatage.util.CollectionUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.annotation.ElementType;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class TestTypeCoercer extends Assert {
	private TypeCoercer typeCoercer;

	@DataProvider
	public Object[][] typeCoercerData() {
		return new Object[][]{
				{null, String.class, null},
				{null, Boolean.class, null},
				{null, Integer.class, null},
				{null, TimeUnit.class, null},
				{null, TypeCoercer.class, null},

				{true, String.class, "true"},
				{Boolean.TRUE, String.class, "true"},
				{false, String.class, "false"},
				{Boolean.FALSE, String.class, "false"},

				{0, String.class, "0"},
				{-0, String.class, "0"},
				{100045, String.class, "100045"},
				{-145, String.class, "-145"},

				{TimeUnit.SECONDS, String.class, "seconds"},
				{TimeUnit.MILLISECONDS, CharSequence.class, "milliseconds"},
				{ElementType.TYPE, String.class, "type"},

				{"type", ElementType.class, ElementType.TYPE},
				{"seconds", TimeUnit.class, TimeUnit.SECONDS},
				{"SECONDS", TimeUnit.class, TimeUnit.SECONDS},
				{"sEcoNds", TimeUnit.class, TimeUnit.SECONDS},

				{-145, CharSequence.class, "-145"},
		};
	}

	@DataProvider
	public Object[][] typeCoercerWrongData() {
		return new Object[][]{
				{"1", null},
				{"1", TypeCoercer.class},
		};
	}

	@BeforeTest
	public void setupTypeCoercer() {
		final Set<Coercion> coercions = CollectionUtils.newSet();
		coercions.add(new BooleanToStringCoercion());
		coercions.add(new NumberToStringCoercion());
		coercions.add(new EnumToStringCoercion());
		coercions.add(new StringToBooleanCoercion());
		coercions.add(new StringToIntegerCoercion());
		coercions.add(new StringToDoubleCoercion());
		final CoercionProvider coercionProvider = new DefaultCoercionProvider(coercions);

		final Set<CoercionProvider> coercionProviders = CollectionUtils.newSet();
		coercionProviders.add(coercionProvider);
		coercionProviders.add(new StringToEnumCoercionProvider());

		typeCoercer = new TypeCoercerImpl(coercionProviders);
	}

	@Test(dataProvider = "typeCoercerData")
	public <S, T> void testTypeCoercer(final S source, final Class<T> targetClass, final T expected) {
		final T actual = typeCoercer.coerce(source, targetClass);
		assertEquals(actual, expected);
	}

	@Test(dataProvider = "typeCoercerWrongData", expectedExceptions = {AssertionError.class, CoerceException.class})
	public <S, T> void testTypeCoercer(final S source, final Class<T> targetClass) {
		typeCoercer.coerce(source, targetClass);
	}
}
