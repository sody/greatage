package org.greatage.ioc.cache;

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
public class TestOSCacheSource extends Assert {
	private CacheSource cacheSource;

	@DataProvider
	public Object[][] getCacheData() {
		return new Object[][]{
				{String.class, Integer.class, CollectionUtils.newMap("1", 1, "2", 2, "3", 3, "4", 4)},
//				{Object.class, Object.class, CollectionUtils.newMap("1", null, "2", null, "3", null, "4", null)},
				{String.class, String.class, CollectionUtils.newMap("1", "11", "2", "12", "3", "13", "4", "14")},
		};
	}

	@BeforeClass
	public void setupCacheManager() {
		cacheSource = new OSCacheSource(true, false, true);
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
