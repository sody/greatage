package org.greatage.util;

import org.greatage.mock.MockOrderedObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestOrderingUtils extends Assert {

	@DataProvider
	public Object[][] orderData() {
		return new Object[][]{
				{CollectionUtils.newList(
						new MockOrderedObject("4", "before:5"),
						new MockOrderedObject("3", "before:4"),
						new MockOrderedObject("1", "before:2"),
						new MockOrderedObject("5"),
						new MockOrderedObject("2", "before:3")
				), CollectionUtils.newList("1", "2", "3", "4", "5")},
				{CollectionUtils.newList(
						new MockOrderedObject("3", "after:2"),
						new MockOrderedObject("5", "after:4"),
						new MockOrderedObject("1"),
						new MockOrderedObject("4", "after:3"),
						new MockOrderedObject("2", "after:1")
				), CollectionUtils.newList("1", "2", "3", "4", "5")},
				{CollectionUtils.newList(
						new MockOrderedObject("2", "before:3", "after:1"),
						new MockOrderedObject("4"),
						new MockOrderedObject("5", "after:4"),
						new MockOrderedObject("3", "before:5", "after:1"),
						new MockOrderedObject("1")
				), CollectionUtils.newList("1", "2", "3", "4", "5")},
		};
	}

	@DataProvider
	public Object[][] orderWrongData() {
		return new Object[][]{
				{CollectionUtils.newList(
						new MockOrderedObject("1"),
						new MockOrderedObject("2"),
						new MockOrderedObject("1")
				)},
				{CollectionUtils.newList(
						new MockOrderedObject("1"),
						new MockOrderedObject("2"),
						new MockOrderedObject("3", "hi:test")
				)},
				{CollectionUtils.newList(
						new MockOrderedObject("1"),
						new MockOrderedObject("2"),
						new MockOrderedObject("3", "before:test")
				)},
				{CollectionUtils.newList(
						new MockOrderedObject("1", "before:1"),
						new MockOrderedObject("2"),
						new MockOrderedObject("3")
				)},
				{CollectionUtils.newList(
						new MockOrderedObject("1", "after:2", "before:2"),
						new MockOrderedObject("2"),
						new MockOrderedObject("3")
				)},
				{CollectionUtils.newList(
						new MockOrderedObject("1", "before:2"),
						new MockOrderedObject("2", "before:3"),
						new MockOrderedObject("3", "before:1")
				)},
		};
	}

	@Test(dataProvider = "orderData")
	public void testOrder(final List<Ordered> items, final List<String> expected) {
		final List<Ordered> actual = OrderingUtils.order(items);
		assertNotNull(actual);
		assertEquals(actual.size(), expected.size());
		for (int i = 0; i < actual.size(); i++) {
			Ordered ordered = actual.get(i);
			assertNotNull(ordered);
			assertEquals(ordered.getOrderId(), expected.get(i));
		}
	}

	@Test(dataProvider = "orderWrongData", expectedExceptions = IllegalArgumentException.class)
	public void testOrderWrong(final List<Ordered> items) {
		OrderingUtils.order(items);
	}
}
