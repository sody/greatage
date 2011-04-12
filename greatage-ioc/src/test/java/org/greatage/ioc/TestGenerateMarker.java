package org.greatage.ioc;

import org.greatage.ioc.annotations.Contribute;
import org.greatage.ioc.annotations.Named;
import org.greatage.ioc.annotations.NamedImpl;
import org.greatage.ioc.annotations.Service;
import org.greatage.ioc.mock.MockIOCInterface;
import org.greatage.ioc.mock.MockIOCInterfaceEx;
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
						MockIOCInterface.class,
						new Annotation[] { named("test") },
						marker(MockIOCInterface.class, named("test"))
				},
				{
						MockIOCInterface.class,
						new Annotation[] { service(MockIOCInterfaceEx.class), named("test") },
						marker(MockIOCInterfaceEx.class, named("test"))
				},
				{
						MockIOCInterface.class,
						new Annotation[] { contribute(), service(MockIOCInterfaceEx.class), named("test") },
						marker(MockIOCInterfaceEx.class, named("test"))
				},


				{
						MockIOCInterface.class,
						null,
						marker(MockIOCInterface.class, null)
				},
				{
						MockIOCInterfaceImpl1.class,
						null,
						marker(MockIOCInterfaceImpl1.class, null)
				},
				{
						MockIOCInterface.class,
						new Annotation[] { },
						marker(MockIOCInterface.class, null)
				},
				{
						MockIOCInterfaceImpl1.class,
						new Annotation[] { },
						marker(MockIOCInterfaceImpl1.class, null)
				},
				{
						MockIOCInterface.class,
						new Annotation[] { service(void.class) },
						marker(MockIOCInterface.class, null)
				},
				{
						MockIOCInterfaceImpl1.class,
						new Annotation[] { service(void.class) },
						marker(MockIOCInterfaceImpl1.class, null)
				},
				{
						MockIOCInterface.class,
						new Annotation[] { service(MockIOCInterfaceEx.class) },
						marker(MockIOCInterfaceEx.class, null)
				},
				{
						MockIOCInterfaceEx.class,
						new Annotation[] { service(MockIOCInterface.class) },
						marker(MockIOCInterface.class, null)
				},
				{
						MockIOCInterfaceImpl1.class,
						new Annotation[] { service(MockIOCInterfaceEx.class) },
						marker(MockIOCInterfaceEx.class, null)
				},
				{
						null,
						new Annotation[] { service(MockIOCInterface.class) },
						marker(MockIOCInterface.class, null)
				},
				{
						null,
						new Annotation[] { service(MockIOCInterfaceImpl1.class) },
						marker(MockIOCInterfaceImpl1.class, null)
				},

				{
						MockIOCInterface.class,
						new Annotation[] { named("test") },
						marker(MockIOCInterface.class, named("test"))
				},
				{
						MockIOCInterface.class,
						new Annotation[] { service(MockIOCInterfaceEx.class), named("test") },
						marker(MockIOCInterfaceEx.class, named("test"))
				},
				{
						MockIOCInterface.class,
						new Annotation[] { contribute(), service(MockIOCInterfaceEx.class), named("test") },
						marker(MockIOCInterfaceEx.class, named("test"))
				},
		};
	}

	@DataProvider
	public Object[][] testGenerateMarkerWrongData() {
		return new Object[][] {
				{
						null,
						new Annotation[] { service(void.class) },
				},
				{
						void.class,
						new Annotation[] { service(void.class) },
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

	private <T> Marker<T> marker(final Class<T> serviceClass, final Annotation annotation) {
		return new Marker<T>(serviceClass, annotation);
	}

	private Service service(final Class serviceClass) {
		return new Service() {
			public Class value() {
				return serviceClass;
			}

			public Class<? extends Annotation> annotationType() {
				return Service.class;
			}
		};
	}

	private Named named(final String name) {
		return new NamedImpl(name);
	}

	private Contribute contribute() {
		return new Contribute() {
			public Class<? extends Annotation> annotationType() {
				return Contribute.class;
			}
		};
	}
}
