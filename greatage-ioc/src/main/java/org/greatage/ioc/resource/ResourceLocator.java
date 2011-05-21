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

import java.util.Set;

/**
 * This interface represents service for working with application hierarchical resources. It also provides
 * functionality of resource filtering.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ResourceLocator {

	/**
	 * Searches application resource by its location.
	 *
	 * @param path resource location, not <code>null</code>
	 * @return resource instance or <code>null</code> if not found
	 */
	Resource getResource(String path);

	/**
	 * Searches for application resources in defined location with specified include filter. If include patterns is
	 * empty, resources will not be filtered.
	 *
	 * @param path	 resource location, not <code>null</code>
	 * @param includes include patterns
	 * @return set of found resource or empty set if not found
	 */
	Set<Resource> findResources(String path, String... includes);

	/**
	 * Searches for application resources in defined location with specified include and exclude filters. If include
	 * or exclude patterns is empty they will be ignored.
	 *
	 * @param path	 resource location, not <code>null</code>
	 * @param includes include patterns, can be <code>null</code>
	 * @param excludes exclude patterns, can be <code>null</code>
	 * @return set of found resource or empty set if not found
	 */
	Set<Resource> findResources(String path, Set<String> includes, Set<String> excludes);
}
