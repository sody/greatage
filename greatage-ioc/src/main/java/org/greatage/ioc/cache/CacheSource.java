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
