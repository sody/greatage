/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.cache;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface CacheSource {

	<K, V> Cache<K, V> getCache(String name);

}
