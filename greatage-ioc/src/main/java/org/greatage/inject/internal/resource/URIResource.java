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

package org.greatage.inject.internal.resource;

import org.greatage.inject.services.Resource;
import org.greatage.util.CollectionUtils;
import org.greatage.util.PathSearcher;

import java.net.URI;
import java.net.URL;
import java.util.Locale;
import java.util.Set;

/**
 * This class represents {@link Resource} implementation that is based on URI. It also can be used as {@link org.greatage.inject.services.ResourceProvider}
 * implementation.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class URIResource extends AbstractResource {

	/**
	 * Resource provider identifier used inside configuration processes.
	 */
	public static final String ID = "uri";

	private static final URIResource ROOT = new URIResource(null, "uri://", null, null);
	private static final String FILE_URI = "file://";

	/**
	 * Creates new resource by specified absolute URI. This is helper method for simplified URI resource creation.
	 *
	 * @param uri absolute uri, not {@code null}
	 * @return new instance of URI resource, not {@code null}
	 */
	public static Resource get(final String uri) {
		return ROOT.getResource(uri);
	}

	/**
	 * Obtains filesystem resource. This is helper method for simplified filesystem resource resolution.
	 *
	 * @param file absolute file path, not {@code null}
	 * @return new instance of filesystem resource, not {@code null}
	 */
	public static Resource file(final String file) {
		return get(FILE_URI + file);
	}

	/**
	 * Obtains root URI resource. This is helper method for simplified root URI resource resolution. Can be used only as {@link
	 * org.greatage.inject.services.ResourceProvider} service.
	 *
	 * @return new instance of URI root resource, not {@code null}
	 */
	public static URIResource root() {
		return ROOT;
	}

	/**
	 * Creates new instance of URI resource with defined location, parent resource, name and locale.
	 *
	 * @param location resource location, can be {@code null}
	 * @param name resource name, not {@code null}
	 * @param type resource type, can be {@code null}
	 * @param locale resource locale, can be {@code null}
	 */
	private URIResource(final String location, final String name, final String type, final Locale locale) {
		super(location, name, type, locale);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected URL toURL() {
		try {
			final URI uri = new URI(getPath());
			return uri.toURL();
		} catch (Exception ex) {
			//todo: log warning
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Resource create(final String location, final String name, final String type, final Locale locale) {
		return new URIResource(location, name, type, locale);
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<Resource> children(final Set<String> includes, final Set<String> excludes) {
		final Set<Resource> children = CollectionUtils.newSet();
		if (exists()) {
			final URL url = toURL();
			if (url != null) {
				// find in absolute path
				final Set<String> resourceNames = PathSearcher.create(url.toExternalForm())
						.include(includes)
						.exclude(excludes)
						.search();

				// add all children
				for (String resourceName : resourceNames) {
					final Resource resource = child(resourceName);
					if (resource != null && resource.exists()) {
						children.add(resource);
					}
				}
			}
		}
		return children;
	}
}
