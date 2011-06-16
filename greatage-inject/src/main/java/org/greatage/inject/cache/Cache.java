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

/**
 * This interface represents cache that provides simplified access to all cache operations.
 *
 * @param <K> type of cache keys
 * @param <V> type of cache items
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Cache<K, V> {

	/**
	 * Gets cache name.
	 *
	 * @return cache name
	 */
	String getName();

	/**
	 * Checks if this cache instance contains item with specified key.
	 *
	 * @param key item key
	 * @return true it this cache instance contains item with specified key, false otherwise
	 */
	boolean contains(K key);

	/**
	 * Gets cached item by its key.
	 *
	 * @param key cache item key
	 * @return cached item or null if not exists
	 */
	V get(K key);

	/**
	 * Puts item to cache.
	 *
	 * @param key   item key
	 * @param value item instance
	 */
	void put(K key, V value);

	/**
	 * Removes cached item by its key.
	 *
	 * @param key cached item key
	 * @return removed item instance or null if not exists
	 */
	V remove(K key);

	/**
	 * Clears cache. All cached items will be removed from it.
	 */
	void clear();
}
