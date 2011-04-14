package org.greatage.ioc;

import org.greatage.ioc.annotations.NamedImpl;
import org.greatage.ioc.annotations.Qualifier;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class TestMarker extends Assert {

	@DataProvider
	public Object[][] testAssignableFromData() {
		return new Object[][] {
				{ Marker.get(Object.class), Marker.get(Object.class), true },
				{ Marker.get(Object.class), Marker.get(MockInterface.class), true },
				{ Marker.get(MockInterface.class), Marker.get(Object.class), false },
				{ Marker.get(MockInterface.class), Marker.get(MockInterfaceEx.class), true },
				{ Marker.get(MockInterface.class), Marker.get(MockInterfaceEx.class), true },
				{ Marker.get(MockInterface.class), Marker.get(MockInterface.class), true },

				{ Marker.get(MockInterface.class), Marker.get(MockInterface.class, mockMarker()), true },
				{ Marker.get(MockInterface.class), Marker.get(MockInterface.class, MockMarker.class), true },
				{ Marker.get(MockInterface.class), Marker.get(MockInterface.class, "test"), true },

				{ Marker.get(MockInterface.class, mockMarker()), Marker.get(MockInterface.class), false },
				{ Marker.get(MockInterface.class, MockMarker.class), Marker.get(MockInterface.class), false },
				{ Marker.get(MockInterface.class, "test"), Marker.get(MockInterface.class), false },

				{ Marker.get(MockInterface.class, mockMarker()), Marker.get(MockInterface.class, "test"), false },
				{ Marker.get(MockInterface.class, mockMarker()), Marker.get(MockInterface.class, MockMarker.class), true },
				{ Marker.get(MockInterface.class, MockMarker.class), Marker.get(MockInterface.class, "test"), false },
				{ Marker.get(MockInterface.class, MockMarker.class), Marker.get(MockInterface.class, mockMarker()), true },
				{ Marker.get(MockInterface.class, "test"), Marker.get(MockInterface.class, MockMarker.class), false },
				{ Marker.get(MockInterface.class, "test"), Marker.get(MockInterface.class, mockMarker()), false },
		};
	}

	@DataProvider
	public Object[][] equalsData() {
		return new Object[][] {
				{ Marker.get(MockInterface.class), Marker.get(MockInterface.class), true },
				{ Marker.get(MockInterface.class), Marker.get(MockInterfaceEx.class), false },

				{ Marker.get(MockInterface.class, mockMarker()), Marker.get(MockInterface.class, mockMarker()), true },
				{ Marker.get(MockInterface.class, mockMarker()), Marker.get(MockInterfaceEx.class, mockMarker()), false },
				{ Marker.get(MockInterface.class, mockMarker()), Marker.get(MockInterface.class), false },

				{ Marker.get(MockInterface.class, MockMarker.class), Marker.get(MockInterface.class, MockMarker.class), true },
				{ Marker.get(MockInterface.class, MockMarker.class), Marker.get(MockInterface.class, mockMarker()), true },
				{ Marker.get(MockInterface.class, MockMarker.class), Marker.get(MockInterfaceEx.class, MockMarker.class), false },
				{ Marker.get(MockInterface.class, MockMarker.class), Marker.get(MockInterface.class), false },

				{ Marker.get(MockInterface.class, "test"), Marker.get(MockInterface.class, "test"), true },
				{ Marker.get(MockInterface.class, "test"), Marker.get(MockInterface.class, new NamedImpl("test")), true },
				{ Marker.get(MockInterface.class, "test"), Marker.get(MockInterface.class, "test2"), false },
				{ Marker.get(MockInterface.class, "test"), Marker.get(MockInterfaceEx.class, "test"), false },
				{ Marker.get(MockInterface.class, "test"), Marker.get(MockInterface.class), false },
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

	private MockMarker mockMarker() {
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
