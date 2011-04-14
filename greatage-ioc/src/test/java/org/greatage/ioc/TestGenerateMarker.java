package org.greatage.ioc;

import org.greatage.ioc.annotations.Named;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class TestGenerateMarker extends Assert {

	@DataProvider
	public Object[][] testGenerateMarkerData() {
		return new Object[][] {
				{ void.class, null, Marker.get(Object.class) },
				{ void.class, MockBean.class.getAnnotations(), Marker.get(Object.class,"test") },

				{ MockInterface.class, MockBean.class.getAnnotations(), Marker.get(MockInterface.class, "test") },

				{ MockInterface.class, null, Marker.get(MockInterface.class) },
				{ MockClass.class, null, Marker.get(MockClass.class) },
				{ MockInterface.class, new Annotation[] { }, Marker.get(MockInterface.class) },
				{ MockClass.class, new Annotation[] { }, Marker.get(MockClass.class) },
		};
	}

	@DataProvider
	public Object[][] testGenerateMarkerWrongData() {
		return new Object[][] {
				{ null, null, },
		};
	}

	@Test(dataProvider = "testGenerateMarkerData")
	public <T> void testGenerateMarker(final Class<T> serviceClass,
									   final Annotation[] annotations,
									   final Marker<T> expected) {
		final Marker<T> actual = InternalUtils.generateMarker(serviceClass, annotations);
		assertNotNull(actual);

		assertEquals(actual.getServiceClass(), expected.getServiceClass());
		assertEquals(actual.getAnnotation(), expected.getAnnotation());
		assertEquals(actual, expected);
	}

	@Test(dataProvider = "testGenerateMarkerWrongData", expectedExceptions = IllegalArgumentException.class)
	public <T> void testGenerateMarkerWrong(final Class<T> serviceClass,
											final Annotation[] annotations) {
		InternalUtils.generateMarker(serviceClass, annotations);
	}

	static interface MockInterface {
		String say(final String message);
	}

	static class MockClass implements MockInterface {
		public String say(final String message) {
			return message;
		}
	}

	@Named("test")
	static class MockBean {
	}
}
