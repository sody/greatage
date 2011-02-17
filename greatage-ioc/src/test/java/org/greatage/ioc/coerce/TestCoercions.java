package org.greatage.ioc.coerce;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class TestCoercions extends Assert {

	@DataProvider
	public Object[][] coercionData() {
		return new Object[][]{
				{new BooleanToStringCoercion(), Boolean.TRUE, "true"},
				{new BooleanToStringCoercion(), Boolean.FALSE, "false"},

				{new NumberToStringCoercion(), 0, "0"},
				{new NumberToStringCoercion(), -0, "0"},
				{new NumberToStringCoercion(), 12, "12"},
				{new NumberToStringCoercion(), -482, "-482"},
				{new NumberToStringCoercion(), Integer.MIN_VALUE, "-2147483648"},
				{new NumberToStringCoercion(), Integer.MAX_VALUE, "2147483647"},

				{new EnumToStringCoercion<TimeUnit>(TimeUnit.class), TimeUnit.SECONDS, "seconds"},

				{new StringToBooleanCoercion(), "true", true},
				{new StringToBooleanCoercion(), "TRUE", true},
				{new StringToBooleanCoercion(), "True", true},
				{new StringToBooleanCoercion(), "TrUe", true},

				{new StringToBooleanCoercion(), "false", false},
				{new StringToBooleanCoercion(), "FALSE", false},
				{new StringToBooleanCoercion(), "False", false},
				{new StringToBooleanCoercion(), "FalSe", false},

				{new StringToIntegerCoercion(), "0", 0},
				{new StringToIntegerCoercion(), "-0", 0},
				{new StringToIntegerCoercion(), "12", 12},
				{new StringToIntegerCoercion(), "-482", -482},
				{new StringToIntegerCoercion(), "-2147483648", Integer.MIN_VALUE},
				{new StringToIntegerCoercion(), "2147483647", Integer.MAX_VALUE},

				{new StringToDoubleCoercion(), "0", 0d},
				{new StringToDoubleCoercion(), "0.1", 0.1d},
				{new StringToDoubleCoercion(), "-234.8", -234.8d},

				{new StringToEnumCoercion<TimeUnit>(TimeUnit.class), "seconds", TimeUnit.SECONDS},
				{new StringToEnumCoercion<TimeUnit>(TimeUnit.class), "MILLISECONDS", TimeUnit.MILLISECONDS},
				{new StringToEnumCoercion<TimeUnit>(TimeUnit.class), "Minutes", TimeUnit.MINUTES},
				{new StringToEnumCoercion<TimeUnit>(TimeUnit.class), "hOuRS", TimeUnit.HOURS},
		};
	}

	@DataProvider
	public Object[][] coercionWrongData() {
		return new Object[][]{
				{new StringToBooleanCoercion(), ""},
				{new StringToBooleanCoercion(), "some string"},

				{new StringToIntegerCoercion(), ""},
				{new StringToIntegerCoercion(), "2as"},
				{new StringToIntegerCoercion(), "2147483648"},
				{new StringToIntegerCoercion(), "-2147483649"},

				{new StringToDoubleCoercion(), ""},
				{new StringToDoubleCoercion(), "0s3"},

				{new StringToEnumCoercion<TimeUnit>(TimeUnit.class), ""},
				{new StringToEnumCoercion<TimeUnit>(TimeUnit.class), "0Uh"},
		};
	}

	@Test(dataProvider = "coercionData")
	public <S, T> void testCoercion(final Coercion<S, T> coercion, final S source, final T expected) {
		final T actual = coercion.coerce(source);
		assertEquals(actual, expected);
	}

	@Test(dataProvider = "coercionWrongData", expectedExceptions = CoerceException.class)
	public <S, T> void testCoercionWrong(final Coercion<S, T> coercion, final S source) {
		coercion.coerce(source);
	}
}
