/*
 * Copyright 2011 Ivan Khalopik
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestCollectionUtils extends Assert {

	@DataProvider
	public Object[][] isEmptyData() {
		return new Object[][]{
				{null, true},
				{new ArrayList(), true},
				{CollectionUtils.newConcurrentList(), true},
				{CollectionUtils.newConcurrentSet(), true},
				{CollectionUtils.newList(), true},
				{CollectionUtils.newSet(), true},
				{CollectionUtils.newSet("aaa", "bbb"), false},
				{CollectionUtils.newList("aaa"), false},
				{CollectionUtils.newConcurrentList("aaa"), false},
		};
	}

	@DataProvider
	public Object[][] collectionFactoryData() {
		return new Object[][]{
				{
						CollectionUtils.newList(),
						ArrayList.class,
						new Object[]{}
				},
				{
						CollectionUtils.newList(1, 2, 3),
						ArrayList.class,
						new Object[]{1, 2, 3}
				},
				{
						CollectionUtils.newList(Arrays.asList("1", "2", "3")),
						ArrayList.class,
						new Object[]{"1", "2", "3"}
				},
				{
						CollectionUtils.newConcurrentList(),
						CopyOnWriteArrayList.class,
						new Object[]{}
				},
				{
						CollectionUtils.newConcurrentList(1, 2, 3),
						CopyOnWriteArrayList.class,
						new Object[]{1, 2, 3}
				},
				{
						CollectionUtils.newConcurrentList(Arrays.asList("1", "2", "3")),
						CopyOnWriteArrayList.class,
						new Object[]{"1", "2", "3"}
				},
				{
						CollectionUtils.newSet(),
						HashSet.class,
						new Object[]{}
				},
				{
						CollectionUtils.newSet(1, 2, 3),
						HashSet.class,
						new Object[]{1, 2, 3}
				},
				{
						CollectionUtils.newSet(Arrays.asList("1", "2", "3")),
						HashSet.class,
						new Object[]{"1", "2", "3"}
				},
				{
						CollectionUtils.newConcurrentSet(),
						CopyOnWriteArraySet.class,
						new Object[]{}
				},
				{
						CollectionUtils.newConcurrentSet(1, 2, 3),
						CopyOnWriteArraySet.class,
						new Object[]{1, 2, 3}
				},
				{
						CollectionUtils.newConcurrentSet(Arrays.asList("1", "2", "3")),
						CopyOnWriteArraySet.class,
						new Object[]{"1", "2", "3"}
				},
		};
	}

	@DataProvider
	public Object[][] mapFactoryData() {
		final Map<String, Integer> testMap = new HashMap<String, Integer>();
		testMap.put("1", 1);
		testMap.put("2", 2);
		testMap.put("3", 3);

		return new Object[][]{
				{CollectionUtils.newMap(), HashMap.class, Collections.emptyMap()},
				{CollectionUtils.newMap("1", 1, "2", 2, "3", 3), HashMap.class, testMap},
				{CollectionUtils.newMap(testMap), HashMap.class, testMap},
				{CollectionUtils.newConcurrentMap(), ConcurrentHashMap.class, Collections.emptyMap()},
				{CollectionUtils.newConcurrentMap("1", 1, "2", 2, "3", 3), ConcurrentHashMap.class, testMap},
				{CollectionUtils.newConcurrentMap(testMap), ConcurrentHashMap.class, testMap},
		};
	}

	@DataProvider
	public Object[][] mapFactoryWrongData() {
		return new Object[][]{
				{null},
				{new Object[]{1, 2, 3}},
		};
	}

	@Test(dataProvider = "isEmptyData")
	public void testIsEmpty(final Collection collection, final boolean expected) {
		final boolean actual = CollectionUtils.isEmpty(collection);
		assertEquals(actual, expected);
	}

	@Test(dataProvider = "collectionFactoryData")
	public void testCollectionFactory(final Collection collection,
									  final Class<? extends Collection> collectionClass,
									  final Object[] expected) {
		assertNotNull(collection);
		assertTrue(collectionClass.isInstance(collection));
		assertEquals(collection.size(), expected.length);
		assertTrue(collection.containsAll(Arrays.asList(expected)));
	}

	@Test(dataProvider = "mapFactoryData")
	public void testMapFactory(final Map map,
							   final Class<? extends Map> mapClass,
							   final Map expected) {
		assertNotNull(map);
		assertTrue(mapClass.isInstance(map));
		assertEquals(map, expected);
	}

	@Test(dataProvider = "mapFactoryWrongData", expectedExceptions = IllegalArgumentException.class)
	public void testMapFactoryWrong(final Object[] values) {
		CollectionUtils.newMap(values);
	}
}
