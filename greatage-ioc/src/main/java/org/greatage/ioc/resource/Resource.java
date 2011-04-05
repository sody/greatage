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

/**
 * This interface represents application hierarchical localized resource that can be represented as input stream.
 *
 * @author Ivan Khalopik
 * @since 1.1
 */
public interface Resource {

	/**
	 * Gets resource name.
	 *
	 * @return resource name
	 */
	String getName();

	/**
	 * Gets parent resource.
	 *
	 * @return instance of parent resource or null if it is in the root
	 */
	Resource getParent();

	/**
	 * Gets resource locale for localized resources.
	 *
	 * @return resource locale or null for non localized resources
	 */
	Locale getLocale();

	/**
	 * Checks if this resource exists.
	 *
	 * @return true if resource exists, false otherwise
	 */
	boolean exists();

	/**
	 * Gets a child resource of this resource by specified relative path.
	 *
	 * @param path relative path to child resource
	 * @return child resource instance, not null
	 */
	Resource getChild(String path);

	/**
	 * Gets the same resource in specified locale.
	 *
	 * @param locale resource locale
	 * @return the same resource in specified locale or null if not exists
	 */
	Resource inLocale(Locale locale);

	/**
	 * Opens resource as an input stream.
	 *
	 * @return resource input stream representation or null if not exists
	 * @throws IOException if error occurs during resource opening
	 */
	InputStream open() throws IOException;
}
