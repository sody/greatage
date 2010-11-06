/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.cache;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Cache<K, V> {

	V get(K key);

	void put(K key, V value);

	boolean contains(K key);

	void invalidate();

}
