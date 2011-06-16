package org.greatage.ioc;

import org.greatage.ioc.annotations.NamedImpl;
import org.greatage.ioc.annotations.Qualifier;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestMarker extends Assert {

	@DataProvider
	public Object[][] testAssignableFromData() {
		return new Object[][]{
				{marker(Object.class), marker(Object.class), true},
				{marker(Object.class), marker(MockInterface.class), true},
				{marker(MockInterface.class), marker(Object.class), false},
				{marker(MockInterface.class), marker(MockInterfaceEx.class), true},
				{marker(MockInterface.class), marker(MockInterfaceEx.class), true},
				{marker(MockInterface.class), marker(MockInterface.class), true},

				{marker(MockInterface.class), marker(MockInterface.class, mockQualifier()), true},
				{marker(MockInterface.class), marker(MockInterface.class, MockMarker.class), true},
				{marker(MockInterface.class), marker(MockInterface.class, "test"), true},

				{marker(MockInterface.class, mockQualifier()), marker(MockInterface.class), false},
				{marker(MockInterface.class, MockMarker.class), marker(MockInterface.class), false},
				{marker(MockInterface.class, "test"), marker(MockInterface.class), false},

				{marker(MockInterface.class, mockQualifier()), marker(MockInterface.class, "test"), false},
				{marker(MockInterface.class, mockQualifier()), marker(MockInterface.class, MockMarker.class), true},
				{marker(MockInterface.class, MockMarker.class), marker(MockInterface.class, "test"), false},
				{marker(MockInterface.class, MockMarker.class), marker(MockInterface.class, mockQualifier()), true},
				{marker(MockInterface.class, "test"), marker(MockInterface.class, MockMarker.class), false},
				{marker(MockInterface.class, "test"), marker(MockInterface.class, mockQualifier()), false},
		};
	}

	@DataProvider
	public Object[][] equalsData() {
		return new Object[][]{
				{marker(MockInterface.class), marker(MockInterface.class), true},
				{marker(MockInterface.class), marker(MockInterfaceEx.class), false},

				{marker(MockInterface.class, mockQualifier()), marker(MockInterface.class, mockQualifier()), true},
				{marker(MockInterface.class, mockQualifier()), marker(MockInterfaceEx.class, mockQualifier()), false},
				{marker(MockInterface.class, mockQualifier()), marker(MockInterface.class), false},

				{marker(MockInterface.class, MockMarker.class), marker(MockInterface.class, MockMarker.class), true},
				{marker(MockInterface.class, MockMarker.class), marker(MockInterface.class, mockQualifier()), true},
				{marker(MockInterface.class, MockMarker.class), marker(MockInterfaceEx.class, MockMarker.class), false},
				{marker(MockInterface.class, MockMarker.class), marker(MockInterface.class), false},

				{marker(MockInterface.class, "test"), marker(MockInterface.class, "test"), true},
				{marker(MockInterface.class, "test"), marker(MockInterface.class, new NamedImpl("test")), true},
				{marker(MockInterface.class, "test"), marker(MockInterface.class, "test2"), false},
				{marker(MockInterface.class, "test"), marker(MockInterfaceEx.class, "test"), false},
				{marker(MockInterface.class, "test"), marker(MockInterface.class), false},
		};
	}

	@Test(dataProvider = "testAssignableFromData")
	public void testAssignableFrom(final Marker first, final Marker second, final boolean expected) {
		final boolean actual = first.isAssignableFrom(second);
		assertEquals(actual, expected);
	}

	@Test(dataProvider = "equalsData")
	public void testEquals(final Marker first, final Marker second, final boolean expected) {
		boolean actual = first.equals(second);
		assertEquals(actual, expected);
		actual = second.equals(first);
		assertEquals(actual, expected);
	}

	private <T> Marker<T> marker(final Class<T> serviceClass) {
		return Key.get(serviceClass);
	}

	private <T> Marker<T> marker(final Class<T> serviceClass, final Annotation qualifier) {
		return Key.get(serviceClass).withQualifier(qualifier);
	}

	private <T> Marker<T> marker(final Class<T> serviceClass, final Class<? extends Annotation> qualifierClass) {
		return Key.get(serviceClass).withQualifier(qualifierClass);
	}

	private <T> Marker<T> marker(final Class<T> serviceClass, final String name) {
		return Key.get(serviceClass).withName(name);
	}

	private MockMarker mockQualifier() {
		return MockBean.class.getAnnotation(MockMarker.class);
	}

	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@Qualifier
	static @interface MockMarker {
	}

	@MockMarker
	static class MockBean {
	}

	static interface MockInterface {
		String say(final String message);
	}

	static interface MockInterfaceEx extends MockInterface {
		String say();
	}
}
