package org.greatage.ioc;

import org.greatage.ioc.mock.MockIOCInterface;
import org.greatage.ioc.mock.MockIOCInterfaceEx;
import org.greatage.ioc.mock.MockIOCInterfaceExImpl;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class TestAssignableMarker extends Assert {

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

	@Test(dataProvider = "testAssignableFromData")
	public void testAssignableFrom(final Marker first, final Marker second, final boolean expected) {
		final boolean actual = first.isAssignableFrom(second);
		assertEquals(actual, expected);
	}

	private <T> Marker<T> marker(final Class<T> serviceClass, final Class<? extends T> targetClass, final Annotation annotation) {
		return new Marker<T>(serviceClass, targetClass, annotation);
	}
}
