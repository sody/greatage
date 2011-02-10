/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.cache;

/**
 * This interface represents utility producing caches for various cache APIs by their class or name.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface CacheSource {

	/**
	 * Creates or retrieves cache based on specified class.
	 *
	 * @param <K>   type of cache keys
	 * @param <V>   type of cache items
	 * @param clazz class
	 * @return cache instance
	 */
	<K, V> Cache<K, V> getCache(Class clazz);

	/**
	 * Creates or retrieves cache based on specified name.
	 *
	 * @param <K>  type of cache keys
	 * @param <V>  type of cache items
	 * @param name name
	 * @return cache instance
	 */
	<K, V> Cache<K, V> getCache(String name);

}
