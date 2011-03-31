package org.greatage.ioc;

import org.greatage.ioc.mock.MockIOCInterface;
import org.greatage.ioc.mock.MockIOCInterfaceEx;
import org.greatage.ioc.mock.MockIOCInterfaceExImpl;
import org.greatage.ioc.mock.MockIOCInterfaceImpl1;
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
	public Object[][] testAssignableFromData() {
		return new Object[][] {
				{
						marker(Object.class, Object.class, null),
						marker(Object.class, Object.class, null),
						true
				},
				{
						marker(Object.class, Object.class, null),
						marker(MockIOCInterface.class, MockIOCInterface.class, null),
						true
				},
				{
						marker(MockIOCInterface.class, MockIOCInterface.class, null),
						marker(Object.class, Object.class, null),
						false
				},
				{
						marker(MockIOCInterface.class, MockIOCInterfaceEx.class, null),
						marker(MockIOCInterfaceEx.class, MockIOCInterfaceEx.class, null),
						true
				},
				{
						marker(MockIOCInterface.class, MockIOCInterfaceEx.class, null),
						marker(MockIOCInterfaceEx.class, MockIOCInterfaceExImpl.class, null),
						true
				},
				{
						marker(MockIOCInterface.class, MockIOCInterfaceEx.class, null),
						marker(MockIOCInterface.class, MockIOCInterface.class, null),
						false
				},
		};
	}

	@DataProvider
	public Object[][] testGenerateMarkerData() {
		return new Object[][] {
				{
						null,
						null,
						marker(Object.class, Object.class, null)
				},
				{
						MockIOCInterface.class,
						null,
						marker(MockIOCInterface.class, MockIOCInterface.class, null)
				},
				{
						MockIOCInterfaceImpl1.class,
						null,
						marker(MockIOCInterfaceImpl1.class, MockIOCInterfaceImpl1.class, null)
				},

				{
						null,
						new Annotation[] { },
						marker(Object.class, Object.class, null)
				},
				{
						MockIOCInterface.class,
						new Annotation[] { },
						marker(MockIOCInterface.class, MockIOCInterface.class, null)
				},
				{
						MockIOCInterfaceImpl1.class,
						new Annotation[] { },
						marker(MockIOCInterfaceImpl1.class, MockIOCInterfaceImpl1.class, null)
				},

				{
						null,
						new Annotation[] { service(void.class, void.class) },
						marker(Object.class, Object.class, null)
				},
				{
						MockIOCInterface.class,
						new Annotation[] { service(void.class, void.class) },
						marker(MockIOCInterface.class, MockIOCInterface.class, null)
				},
				{
						MockIOCInterfaceImpl1.class,
						new Annotation[] { service(void.class, void.class) },
						marker(MockIOCInterfaceImpl1.class, MockIOCInterfaceImpl1.class, null)
				},

				{
						null,
						new Annotation[] { service(MockIOCInterfaceEx.class, void.class) },
						marker(MockIOCInterfaceEx.class, MockIOCInterfaceEx.class, null)
				},
				{
						MockIOCInterface.class,
						new Annotation[] { service(MockIOCInterfaceEx.class, void.class) },
						marker(MockIOCInterfaceEx.class, MockIOCInterfaceEx.class, null)
				},
				{
						MockIOCInterfaceImpl1.class,
						new Annotation[] { service(MockIOCInterfaceEx.class, void.class) },
						marker(MockIOCInterfaceEx.class, MockIOCInterfaceEx.class, null)
				},

				{
						null,
						new Annotation[] { service(void.class, MockIOCInterfaceEx.class) },
						marker(MockIOCInterfaceEx.class, MockIOCInterfaceEx.class, null)
				},
				{
						MockIOCInterface.class,
						new Annotation[] { service(void.class, MockIOCInterfaceEx.class) },
						marker(MockIOCInterface.class, MockIOCInterfaceEx.class, null)
				},
				{
						MockIOCInterfaceEx.class,
						new Annotation[] { service(void.class, MockIOCInterfaceExImpl.class) },
						marker(MockIOCInterfaceEx.class, MockIOCInterfaceExImpl.class, null)
				},

				{
						null,
						new Annotation[] { service(MockIOCInterfaceEx.class, MockIOCInterfaceEx.class) },
						marker(MockIOCInterfaceEx.class, MockIOCInterfaceEx.class, null)
				},
				{
						MockIOCInterface.class,
						new Annotation[] { service(MockIOCInterfaceEx.class, MockIOCInterfaceEx.class) },
						marker(MockIOCInterfaceEx.class, MockIOCInterfaceEx.class, null)
				},
				{
						MockIOCInterfaceImpl1.class,
						new Annotation[] { service(MockIOCInterfaceEx.class, MockIOCInterfaceEx.class) },
						marker(MockIOCInterfaceEx.class, MockIOCInterfaceEx.class, null)
				},

				{
						null,
						new Annotation[] { service(MockIOCInterfaceEx.class, MockIOCInterfaceExImpl.class) },
						marker(MockIOCInterfaceEx.class, MockIOCInterfaceExImpl.class, null)
				},
				{
						MockIOCInterface.class,
						new Annotation[] { service(MockIOCInterfaceEx.class, MockIOCInterfaceExImpl.class) },
						marker(MockIOCInterfaceEx.class, MockIOCInterfaceExImpl.class, null)
				},
				{
						MockIOCInterfaceImpl1.class,
						new Annotation[] { service(MockIOCInterfaceEx.class, MockIOCInterfaceExImpl.class) },
						marker(MockIOCInterfaceEx.class, MockIOCInterfaceExImpl.class, null)
				},
		};
	}

	@DataProvider
	public Object[][] testGenerateMarkerWrongData() {
		return new Object[][] {
				{
						MockIOCInterfaceImpl1.class,
						new Annotation[] { service(void.class, MockIOCInterfaceEx.class) },
				},
				{
						null,
						new Annotation[] { service(MockIOCInterfaceExImpl.class, MockIOCInterfaceEx.class) },
				},
		};
	}

	@Test(dataProvider = "testAssignableFromData")
	public void testAssignableFrom(final Marker first, final Marker second, final boolean expected) {
		final boolean actual = first.isAssignableFrom(second);
		assertEquals(actual, expected);
	}

	@Test(dataProvider = "testGenerateMarkerData")
	public <T> void testGenerateMarker(final Class<T> defaultClass, final Annotation[] annotations,
									   final Marker<T> expected) {
		final Marker<T> actual = Marker.generate(defaultClass, annotations);
		assertNotNull(actual);

		assertEquals(actual.getServiceClass(), expected.getServiceClass());
		assertEquals(actual.getTargetClass(), expected.getTargetClass());
		assertEquals(actual.getAnnotation(), expected.getAnnotation());
	}

	@Test(dataProvider = "testGenerateMarkerWrongData", expectedExceptions = IllegalArgumentException.class)
	public <T> void testGenerateMarkerWrong(final Class<T> defaultClass, final Annotation[] annotations) {
		Marker.generate(defaultClass, annotations);
	}

	private <T> Marker<T> marker(final Class<T> serviceClass, final Class<? extends T> targetClass, final Annotation annotation) {
		return new Marker<T>(serviceClass, targetClass, annotation);
	}

	private org.greatage.ioc.annotations.Service service(final Class serviceClass, final Class value) {
		return new org.greatage.ioc.annotations.Service() {
			public Class service() {
				return serviceClass;
			}

			public Class value() {
				return value;
			}

			public Class<? extends Annotation> annotationType() {
				return org.greatage.ioc.annotations.Service.class;
			}
		};
	}
}
