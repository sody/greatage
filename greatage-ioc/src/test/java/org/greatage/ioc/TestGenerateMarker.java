package org.greatage.ioc;

import org.greatage.ioc.annotations.Named;
import org.greatage.ioc.internal.InternalUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestGenerateMarker extends Assert {

	@DataProvider
	public Object[][] testGenerateMarkerData() {
		return new Object[][]{
				{void.class, null, Key.get(Object.class)},
				{void.class, MockBean.class.getAnnotations(), Key.get(Object.class).named("test")},

				{MockInterface.class, MockBean.class.getAnnotations(), Key.get(MockInterface.class).named("test")},

				{MockInterface.class, null, Key.get(MockInterface.class)},
				{MockClass.class, null, Key.get(MockClass.class)},
				{MockInterface.class, new Annotation[]{}, Key.get(MockInterface.class)},
				{MockClass.class, new Annotation[]{}, Key.get(MockClass.class)},
		};
	}

	@DataProvider
	public Object[][] testGenerateMarkerWrongData() {
		return new Object[][]{
				{null, null,},
		};
	}

	@Test(dataProvider = "testGenerateMarkerData")
	public <T> void testGenerateMarker(final Class<T> serviceClass,
									   final Annotation[] annotations,
									   final Marker<T> expected) {
		final Marker<T> actual = InternalUtils.generateMarker(serviceClass, annotations);
		assertNotNull(actual);

		assertEquals(actual.getServiceClass(), expected.getServiceClass());
		assertEquals(actual.getQualifier(), expected.getQualifier());
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
