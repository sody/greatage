/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.cache;

/**
 * This interface represents cache that provides simplified access to all cache operations.
 *
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
