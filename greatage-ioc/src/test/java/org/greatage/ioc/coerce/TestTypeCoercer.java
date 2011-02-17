package org.greatage.ioc.coerce;

import org.greatage.util.CollectionUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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
		coercions.add(new StringToBooleanCoercion());
		coercions.add(new StringToIntegerCoercion());
		coercions.add(new StringToDoubleCoercion());
		final CoercionProvider coercionProvider = new DefaultCoercionProvider(coercions);

		final Set<CoercionProvider> coercionProviders = CollectionUtils.newSet();
		coercionProviders.add(coercionProvider);
		coercionProviders.add(new EnumToStringCoercionProvider());
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
