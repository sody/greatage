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

package org.greatage.inject;

/**
 * This class represent mapped service configuration that is used by service contributors to configure service instance.
 * It is injected to service resources as a map of keyed items at build time.
 *
 * @param <K> type of configuration keys
 * @param <V> type of configuration values
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface MappedConfiguration<K, V> {

	/**
	 * Adds simple keyed item to service configuration.
	 *
	 * @param key   item key
	 * @param value item instance
	 * @return this configuration instance
	 */
	MappedConfiguration<K, V> add(K key, V value);

	/**
	 * Instantiates instance of specified class with all injected dependencies and adds this keyed item to service
	 * configuration.
	 *
	 * @param key		item key
	 * @param valueClass item class
	 * @return this configuration instance
	 * @throws ApplicationException if error occurs while creating new instance
	 */
	MappedConfiguration<K, V> addInstance(K key, Class<? extends V> valueClass);
}
