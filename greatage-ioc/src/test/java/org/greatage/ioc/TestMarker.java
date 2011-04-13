package org.greatage.ioc;

import org.greatage.ioc.annotations.NamedImpl;
import org.greatage.ioc.mock.MockIOCInterface;
import org.greatage.ioc.mock.MockIOCInterfaceEx;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class TestMarker extends Assert {

	@DataProvider
	public Object[][] equalsData() {
		return new Object[][] {
				{ Marker.get(MockIOCInterface.class), null, false },

				{ Marker.get(MockIOCInterface.class), Marker.get(MockIOCInterface.class), true },
				{ Marker.get(MockIOCInterface.class), Marker.get(MockIOCInterfaceEx.class), false },

				{ Marker.get(MockIOCInterface.class, mockMarker()), Marker.get(MockIOCInterface.class, mockMarker()), true},
				{ Marker.get(MockIOCInterface.class, mockMarker()), Marker.get(MockIOCInterfaceEx.class, mockMarker()), false},
				{ Marker.get(MockIOCInterface.class, mockMarker()), Marker.get(MockIOCInterface.class), false},

				{ Marker.get(MockIOCInterface.class, MockMarker.class), Marker.get(MockIOCInterface.class, MockMarker.class), true},
				{ Marker.get(MockIOCInterface.class, MockMarker.class), Marker.get(MockIOCInterface.class, mockMarker()), true},
				{ Marker.get(MockIOCInterface.class, MockMarker.class), Marker.get(MockIOCInterfaceEx.class, MockMarker.class), false},
				{ Marker.get(MockIOCInterface.class, MockMarker.class), Marker.get(MockIOCInterface.class), false},

				{ Marker.get(MockIOCInterface.class, "test"), Marker.get(MockIOCInterface.class, "test"), true },
				{ Marker.get(MockIOCInterface.class, "test"), Marker.get(MockIOCInterface.class, new NamedImpl("test")), true },
				{ Marker.get(MockIOCInterface.class, "test"), Marker.get(MockIOCInterface.class, "test2"), false },
				{ Marker.get(MockIOCInterface.class, "test"), Marker.get(MockIOCInterfaceEx.class, "test"), false },
				{ Marker.get(MockIOCInterface.class, "test"), Marker.get(MockIOCInterface.class), false },
		};
	}

	@Test(dataProvider = "equalsData")
	public void testEquals(final Marker first, final Marker second, final boolean expected) {
		final boolean actual = first.equals(second);
		assertEquals(actual, expected);
		if (second != null) {
			assertEquals(first.hashCode(), second.hashCode());
		}
	}

	private MockMarker mockMarker() {
		return new MockMarker() {
			public Class<? extends Annotation> annotationType() {
				return MockMarker.class;
			}
		};
	}
}
