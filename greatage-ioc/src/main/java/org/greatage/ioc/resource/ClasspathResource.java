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

import org.greatage.util.CollectionUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Set;

/**
 * This class represents {@link Resource} implementation that is based on application classpath.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ClasspathResource extends AbstractResource {
	public static final String ID = "classpath";

	private static final ClasspathResource ROOT = new ClasspathResource(null, "", null, null);

	/**
	 * Creates new classpath resource by specified absolute path. This is helper method for simplified classpath resource
	 * creation.
	 *
	 * @param absolutePath classpath absolute path, not <code>null</code>
	 * @return new instance of classpath resource, not <code>null</code>
	 */
	public static Resource get(final String absolutePath) {
		return ROOT.create(absolutePath);
	}

	/**
	 * Obtains root classpath resource. This is helper method for simplified root classpath resource resolution.
	 *
	 * @return new instance of classpath root resource, not <code>null</code>
	 */
	public static Resource root() {
		return ROOT;
	}

	/**
	 * Creates new instance of classpath resource with defined location, parent resource, name and locale.
	 *
	 * @param location resource location, can be <code>null</code>
	 * @param name resource name, not <code>null</code>
	 * @param type resource type, can be <code>null</code>
	 * @param locale resource locale, can be <code>null</code>
	 */
	private ClasspathResource(final String location, final String name, final String type, final Locale locale) {
		super(location, name, type, locale);
	}

	/**
	 * {@inheritDoc} Obtains URL using class loader.
	 */
	@Override
	protected URL toURL() {
		return getClassLoader().getResource(getPath());
	}

	/**
	 * {@inheritDoc} Obtains children for every such resource under classpath.
	 */
	@Override
	public Set<Resource> children(final Set<String> includes, final Set<String> excludes) {
		final Set<Resource> children = CollectionUtils.newSet();
		try {
			final Enumeration<URL> urls = getClassLoader().getResources(getPath());
			while (urls.hasMoreElements()) {
				final URL url = urls.nextElement();
				addFiles(children, url.toExternalForm(), includes, excludes);
			}
		} catch (IOException e) {
			//todo: log warning
		}
		return children;
	}

	/**
	 * {@inheritDoc} Always creates classpath resources.
	 */
	@Override
	protected Resource createResource(final String path, final String name, final String type, final Locale locale) {
		return new ClasspathResource(path, name, type, locale);
	}

	/**
	 * Gets current class loader, Firstly for this class, then for thread and then system class loader.
	 *
	 * @return actual class  loader, not <code>null</code>
	 */
	private ClassLoader getClassLoader() {
		final ClassLoader classLoader = getClass().getClassLoader();
		if (classLoader != null) {
			return classLoader;
		}
		final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		if (contextClassLoader != null) {
			return contextClassLoader;
		}
		return ClassLoader.getSystemClassLoader();
	}
}
