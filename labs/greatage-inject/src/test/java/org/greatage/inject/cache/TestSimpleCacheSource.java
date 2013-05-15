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

package org.greatage.inject.cache;

import org.greatage.util.CollectionUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestSimpleCacheSource extends Assert {
	private CacheSource cacheSource;

	@DataProvider
	public Object[][] getCacheData() {
		return new Object[][]{
				{String.class, Integer.class, CollectionUtils.newMap("1", 1, "2", 2, "3", 3, "4", 4)},
				{Object.class, Object.class, CollectionUtils.newMap("1", null, "2", null, "3", null, "4", null)},
				{String.class, String.class, CollectionUtils.newMap("1", "11", "2", "12", "3", "13", "4", "14")},
		};
	}

	@BeforeClass
	public void setupCacheManager() {
		cacheSource = new SimpleCacheSource();
	}

	@Test(dataProvider = "getCacheData")
	public <K, V> void testGetCache(final Class<K> keyClass, final Class<V> valueClass, final Map<K, V> data) {
		final Cache<K, V> cache = cacheSource.getCache("test");
		assertNotNull(cache);
		assertEquals(cache.getName(), "test");
		cache.clear();
		for (Map.Entry<K, V> entry : data.entrySet()) {
			cache.put(entry.getKey(), entry.getValue());
		}

		for (K key : data.keySet()) {
			assertTrue(cache.contains(key));
		}

		for (Map.Entry<K, V> entry : data.entrySet()) {
			final V actual = cache.get(entry.getKey());
			final V expected = entry.getValue();
			assertEquals(actual, expected);
		}
		cache.clear();
		for (K key : data.keySet()) {
			assertFalse(cache.contains(key));
		}
	}
}
