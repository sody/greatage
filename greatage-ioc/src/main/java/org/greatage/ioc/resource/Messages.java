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

package org.greatage.ioc.resource;

/**
 * This class represents localized strings mapped by keys for particular locale.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Messages {

	/**
	 * Checks if messages contains localized string with specified key.
	 *
	 * @param key localized string key
	 * @return true if messages contains localized string, false otherwise
	 */
	boolean contains(String key);

	/**
	 * Gets localized string with specified key or placeholder if messages doesn't contain key.
	 *
	 * @param key localized string key
	 * @return localized string with specified key or placeholder
	 */
	String get(String key);

	/**
	 * Formats localized string with specified key using parameters.
	 *
	 * @param key		localized string key
	 * @param parameters format parameters
	 * @return formatted localized string
	 */
	String format(String key, Object... parameters);
}
