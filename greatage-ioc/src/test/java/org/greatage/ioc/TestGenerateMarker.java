package org.greatage.ioc;

import org.greatage.ioc.annotations.Named;
import org.greatage.ioc.annotations.NamedImpl;
import org.greatage.ioc.mock.MockIOCInterface;
import org.greatage.ioc.mock.MockIOCInterfaceImpl1;
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
				{
						void.class,
						null,
						Marker.get(Object.class)
				},
				{
						void.class,
						new Annotation[] { named("test") },
						Marker.get(Object.class, named("test"))
				},

				{
						MockIOCInterface.class,
						new Annotation[] { named("test") },
						Marker.get(MockIOCInterface.class, named("test"))
				},

				{
						MockIOCInterface.class,
						null,
						Marker.get(MockIOCInterface.class)
				},
				{
						MockIOCInterfaceImpl1.class,
						null,
						Marker.get(MockIOCInterfaceImpl1.class)
				},
				{
						MockIOCInterface.class,
						new Annotation[] { },
						Marker.get(MockIOCInterface.class)
				},
				{
						MockIOCInterfaceImpl1.class,
						new Annotation[] { },
						Marker.get(MockIOCInterfaceImpl1.class)
				},
		};
	}

	@DataProvider
	public Object[][] testGenerateMarkerWrongData() {
		return new Object[][] {
				{
						null,
						null,
				},
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

	private Named named(final String name) {
		return new NamedImpl(name);
	}
}
