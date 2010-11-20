/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.cache;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Cache<K, V> {

	String getName();

	boolean contains(K key);

	V get(K key);

	void put(K key, V value);

	V remove(K key);

	void clear();

}
