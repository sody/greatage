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

package org.greatage.inject.services;

import java.util.Locale;

/**
 * This interface represents resource retrieval strategy. It provides functionality for resource retrieval by absolute
 * path or by its location, name, type and locale. It is used as configuration for {@link ResourceLocator} service.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ResourceProvider {

	/**
	 * Parses given absolute path to parent location, resource name, type, locale and creates new resource of needed
	 * implementation.
	 *
	 * @param absolutePath absolute resource path, not {@code null}
	 * @return new resource instance for specified absolute path, not {@code null}
	 */
	Resource getResource(String absolutePath);

	/**
	 * Creates new resource instance of needed implementation with defined location, name, type and locale.
	 *
	 * @param location resource location, can be {@code null}
	 * @param name	 resource name, not {@code null}
	 * @param type	 resource type, can be {@code null}
	 * @param locale   resource locale, can be {@code null}
	 * @return new resource instance of needed implementation, not {@code null}
	 */
	Resource getResource(String location, String name, String type, Locale locale);
}
