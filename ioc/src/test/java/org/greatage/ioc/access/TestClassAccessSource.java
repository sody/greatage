package org.greatage.ioc.access;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class TestClassAccessSource extends Assert {
	private ClassAccessSource accessSource;

	@BeforeClass
	public void setupAccessSource() {
		accessSource = new ClassAccessSourceImpl();
	}

	@DataProvider
	public Object[][] propertyAccessGetData() {
		return new Object[][] {
				{MockObject.class, "message", new MockObject("hello"), "hello"},
				{MockObject.class, "type", new MockObject("hello"), 10},
		};
	}

	@DataProvider
	public Object[][] propertyAccessSetData() {
		return new Object[][] {
				{MockObject.class, "type", new MockObject("hello"), 10},
		};
	}

	@Test(dataProvider = "propertyAccessGetData")
	public void testPropertyAccessGet(final Class clazz, final String property, final Object instance,
									  final Object expected) {
		final PropertyAccess access = accessSource.getAccess(clazz).getPropertyAccess(property);
		final Object actual = access.get(instance);
		assertEquals(actual, expected);
	}

	@Test(dataProvider = "propertyAccessSetData")
	public void testPropertyAccessSet(final Class clazz, final String property, final Object instance,
									  final Object expected) {
		final PropertyAccess access = accessSource.getAccess(clazz).getPropertyAccess(property);
		access.set(instance, expected);
		final Object actual = access.get(instance);
		assertEquals(actual, expected);
	}
}
