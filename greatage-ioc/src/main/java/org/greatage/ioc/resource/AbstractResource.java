/*
 * Copyright 2011 Ivan Khalopik
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

import org.greatage.util.I18nUtils;
import org.greatage.util.StringUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Locale;

/**
 * This class represents abstract {@link Resource} implementation that implements all base logic.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractResource implements Resource {
	private static final char FILE_DELIMITER = '.';
	private static final char LOCALE_DELIMITER = '_';
	private static final char FOLDER_DELIMITER = '/';

	private static final String CURRENT_FOLDER = ".";
	private static final String PARENT_FOLDER = "..";

	private final AbstractResource parent;
	private final String name;
	private final Locale locale;

	private String path;

	/**
	 * Creates new resource instance with defined parent resource, name and locale.
	 *
	 * @param parent parent resource
	 * @param name   resource name
	 * @param locale resource locale
	 */
	protected AbstractResource(final AbstractResource parent, final String name, final Locale locale) {
		this.parent = parent;
		this.name = name;
		this.locale = locale;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	public Resource getParent() {
		return parent;
	}

	/**
	 * {@inheritDoc}
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * {@inheritDoc}
	 */
	public Resource inLocale(final Locale resourceLocale) {
		final List<Locale> locales = I18nUtils.getCandidateLocales(resourceLocale);
		for (Locale candidate : locales) {
			if (candidate.equals(locale) && exists()) {
				return this;
			}
			final Resource resource = createResource(parent, name, candidate);
			if (resource.exists()) {
				return resource;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Resource getChild(final String resourcePath) {
		if (StringUtils.isEmpty(resourcePath) || CURRENT_FOLDER.equals(resourcePath)) {
			return this;
		}
		if (PARENT_FOLDER.equals(resourcePath)) {
			return parent;
		}

		final int slashIndex = resourcePath.indexOf(FOLDER_DELIMITER);
		if (slashIndex == -1) {
			return createResource(name.lastIndexOf(FILE_DELIMITER) < 0 ? this : parent, resourcePath, locale);
		} else {
			final Resource child = getChild(resourcePath.substring(0, slashIndex));
			return child.getChild(resourcePath.substring(slashIndex + 1));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean exists() {
		return toURL() != null;
	}

	/**
	 * {@inheritDoc}
	 */
	public InputStream open() throws IOException {
		final URL url = toURL();
		if (url == null) {
			return null;
		}
		return new BufferedInputStream(url.openStream());
	}

	/**
	 * Gets absolute resource path inside the file system.
	 *
	 * @return absolute resource path, not null
	 */
	protected String getPath() {
		if (path == null) {
			final StringBuilder builder = new StringBuilder();

			final String folder = parent != null ? parent.getPath() : null;
			if (!StringUtils.isEmpty(folder)) {
				builder.append(folder).append(FOLDER_DELIMITER);
			}

			final int dotPosition = name.lastIndexOf(FILE_DELIMITER);
			final String file = dotPosition != -1 ? name.substring(0, dotPosition) : name;
			final String extension = dotPosition != -1 ? name.substring(dotPosition) : null;
			builder.append(file);

			if (locale != null && !StringUtils.isEmpty(locale.toString())) {
				builder.append(LOCALE_DELIMITER).append(locale);
			}

			if (!StringUtils.isEmpty(extension)) {
				builder.append(extension);
			}
			path = builder.toString();
		}
		return path;
	}

	/**
	 * Gets URL representation of resource.
	 *
	 * @return resource to URL representation or null
	 */
	protected abstract URL toURL();

	/**
	 * Creates new resource instance of needed implementation with defined parent resource, name and locale.
	 *
	 * @param parentResource parent resource
	 * @param resourceName   resource name
	 * @param resourceLocale resource locale
	 * @return new resource instance of needed implementation
	 */
	protected abstract Resource createResource(final Resource parentResource,
											   final String resourceName,
											   final Locale resourceLocale);
}
