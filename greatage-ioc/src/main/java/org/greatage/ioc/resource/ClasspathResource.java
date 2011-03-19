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

import java.net.URL;
import java.util.Locale;

/**
 * This class represents {@link Resource} implementation that is based on application classpath.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ClasspathResource extends AbstractResource {
	private final ClassLoader classLoader;

	/**
	 * Creates new instance of classpath resource with defined class loader, parent resource, name and locale.
	 *
	 * @param classLoader resource class loader
	 * @param parent	  parent resource
	 * @param name		resource name
	 * @param locale	  resource locale
	 */
	public ClasspathResource(final ClassLoader classLoader,
							 final ClasspathResource parent,
							 final String name,
							 final Locale locale) {
		super(parent, name, locale);
		this.classLoader = classLoader;
	}

	/**
	 * {@inheritDoc} Obtains URL using class loader.
	 */
	@Override
	protected URL toURL() {
		final String path = getPath();
		return classLoader.getResource(path);
	}

	/**
	 * {@inheritDoc} Always creates classpath resources.
	 */
	@Override
	protected Resource createResource(final Resource parentResource,
									  final String resourceName,
									  final Locale resourceLocale) {
		return new ClasspathResource(classLoader, (ClasspathResource) parentResource, resourceName, resourceLocale);
	}
}
