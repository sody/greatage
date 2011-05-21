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

import java.net.URI;
import java.net.URL;
import java.util.Locale;

/**
 * This class represents {@link Resource} implementation that is based on URI.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class URIResource extends AbstractResource {
	private static final URIResource FILE_ROOT = new URIResource(null, "file://", null, null);
	private static final URIResource HTTP_ROOT = new URIResource(null, "http://", null, null);

	public static URIResource file() {
		return FILE_ROOT;
	}

	public static URIResource http() {
		return HTTP_ROOT;
	}

	/**
	 * Creates new instance of URI resource with defined location, parent resource, name and locale.
	 *
	 * @param location resource location, can be <code>null</code>
	 * @param name	 resource name, not <code>null</code>
	 * @param type,	can be <code>null</code>
	 * @param locale   resource locale, can be <code>null</code>
	 */
	public URIResource(final String location, final String name, final String type, final Locale locale) {
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
	protected Resource createResource(final String location, final String name, final String type, final Locale locale) {
		return new URIResource(location, name, type, locale);
	}
}
