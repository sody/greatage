/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.greatage.util;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestOrderingUtils extends Assert {

	@DataProvider
	public Object[][] orderData() {
		return new Object[][]{
				{
						CollectionUtils.newList(
								new MockOrderedObject("4", "before:5"),
								new MockOrderedObject("3", "before:4"),
								new MockOrderedObject("1", "before:2"),
								new MockOrderedObject("5"),
								new MockOrderedObject("2", "before:3")
						), CollectionUtils.newList("1", "2", "3", "4", "5")
				},
				{
						CollectionUtils.newList(
								new MockOrderedObject("3", "after:2"),
								new MockOrderedObject("5", "after:4"),
								new MockOrderedObject("1"),
								new MockOrderedObject("4", "after:3"),
								new MockOrderedObject("2", "after:1")
						), CollectionUtils.newList("1", "2", "3", "4", "5")
				},
				{
						CollectionUtils.newList(
								new MockOrderedObject("2", "before:3", "after:1"),
								new MockOrderedObject("4"),
								new MockOrderedObject("5", "after:4"),
								new MockOrderedObject("3", "before:5", "after:1"),
								new MockOrderedObject("1")
						), CollectionUtils.newList("1", "2", "3", "4", "5")
				},
		};
	}

	@DataProvider
	public Object[][] orderWrongData() {
		return new Object[][]{
				{
						CollectionUtils.newList(
								new MockOrderedObject("1"),
								new MockOrderedObject("2"),
								new MockOrderedObject("1")
						)
				},
				{
						CollectionUtils.newList(
								new MockOrderedObject("1"),
								new MockOrderedObject("2"),
								new MockOrderedObject("3", "hi:test")
						)
				},
				{
						CollectionUtils.newList(
								new MockOrderedObject("1"),
								new MockOrderedObject("2"),
								new MockOrderedObject("3", "before:test")
						)
				},
				{
						CollectionUtils.newList(
								new MockOrderedObject("1"),
								new MockOrderedObject("2", "after:test"),
								new MockOrderedObject("3")
						)
				},
				{
						CollectionUtils.newList(
								new MockOrderedObject("1", "before:1"),
								new MockOrderedObject("2"),
								new MockOrderedObject("3")
						)
				},
				{
						CollectionUtils.newList(
								new MockOrderedObject("1"),
								new MockOrderedObject("2", "after:2"),
								new MockOrderedObject("3")
						)
				},
				{
						CollectionUtils.newList(
								new MockOrderedObject("1", "after:2", "before:2"),
								new MockOrderedObject("2"),
								new MockOrderedObject("3")
						)
				},
				{
						CollectionUtils.newList(
								new MockOrderedObject("1", "before:2"),
								new MockOrderedObject("2", "before:3"),
								new MockOrderedObject("3", "before:1")
						)
				},
				{
						CollectionUtils.newList(
								new MockOrderedObject("1", "after:2"),
								new MockOrderedObject("2", "after:3"),
								new MockOrderedObject("3", "after:1")
						)
				},
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

	public static class MockOrderedObject implements Ordered {
		private final String orderId;
		private final List<String> orderConstraints;

		public MockOrderedObject(final String orderId, final String... orderConstraints) {
			this.orderId = orderId;
			this.orderConstraints = Arrays.asList(orderConstraints);
		}

		public String getOrderId() {
			return orderId;
		}

		public List<String> getOrderConstraints() {
			return orderConstraints;
		}
	}
}
