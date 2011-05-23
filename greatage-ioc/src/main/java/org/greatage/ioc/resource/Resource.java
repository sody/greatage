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

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Set;

/**
 * This interface represents application hierarchical localized resource that can be represented as input stream.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Resource {

	/**
	 * Gets absolute resource path inside the application resource system. It is calculated as
	 * <code>location/name_locale.type</code>.
	 *
	 * @return absolute resource path, not <code>null</code>
	 */
	String getPath();

	/**
	 * Gets absolute resource location inside the application resource system.
	 *
	 * @return absolute resource location, can be <code>null</code>
	 */
	String getLocation();

	/**
	 * Gets resource name.
	 *
	 * @return resource name, not <code>null</code>
	 */
	String getName();

	/**
	 * Gets resource type.
	 *
	 * @return resource type, can be <code>null</code>
	 */
	String getType();

	/**
	 * Gets resource locale for localized resources.
	 *
	 * @return resource locale or <code>null</code> for non localized resources
	 */
	Locale getLocale();

	/**
	 * Checks if this resource exists.
	 *
	 * @return <code>true</code> if resource exists, <code>false</code> otherwise
	 */
	boolean exists();

	/**
	 * Gets the same resource in specified locale. If specified locale is <code>null</code> it will return non
	 * localized resource. If resource in specified locale doesn't exist it will try to find resource in other
	 * candidate locales, e.g. if resource doesn't exist in <code>en_US</code> locale it will try to find it with
	 * <code>en</code> locale and then without locale.
	 *
	 * @param locale resource locale, can be <code>null</code>
	 * @return the same resource in specified locale or <code>null</code> if not exists
	 */
	Resource inLocale(Locale locale);

	/**
	 * Gets the same resource with specified type. If specified type is <code>null</code> it will return resource
	 * without type.
	 *
	 * @param type resource type, can be <code>null</code>
	 * @return the same resource with specified type, <code>null</code>
	 */
	Resource withType(String type);

	/**
	 * Gets parent resource.
	 *
	 * @return instance of parent resource or <code>null</code> if it is in the root
	 */
	Resource parent();

	/**
	 * Gets a child resource of this resource by specified relative path.
	 *
	 * @param relativePath relative path to child resource, not <code>null</code>
	 * @return child resource instance, not <code>null</code>
	 */
	Resource child(String relativePath);

	/**
	 * Searches for child resources with specified include filter. If include patterns is empty, resources will not be filtered.
	 *
	 * @param includes include patterns
	 * @return set of found resource or empty set if not found
	 */
	Set<Resource> children(String... includes);

	/**
	 * Searches for child resources with specified include and exclude filters. If include or exclude patterns is empty they
	 * will be ignored.
	 *
	 * @param includes include patterns, can be <code>null</code>
	 * @param excludes exclude patterns, can be <code>null</code>
	 * @return set of found resource or empty set if not found
	 */
	Set<Resource> children(Set<String> includes, Set<String> excludes);

	/**
	 * Tries to open resource as an input stream and throw {@link IOException} if error occurs during resource opening.
	 *
	 * @return resource input stream representation or <code>null</code> if resource doesn't exist
	 * @throws IOException if error occurs during resource opening
	 */
	InputStream open() throws IOException;
}
