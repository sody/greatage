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

import org.greatage.util.LocaleUtils;
import org.greatage.util.PathUtils;
import org.greatage.util.StringUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents abstract {@link Resource} implementation that implements all base resource logic. All
 * {@link Resource} implementation should be subclasses from it to be completely processed with {@link ResourceLocator}
 * servide.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractResource implements Resource {
	private static final Pattern LOCALE_PATTERN = Pattern.compile("^(.*)_([a-z]{2}(_[A-Z]{2})?)$");

	private final String location;
	private final String name;
	private final String type;
	private final Locale locale;

	private final String path;

	/**
	 * Creates new instance of resource with defined location, name, type and locale. It also will calculate resource
	 * path using {@link PathUtils} utility.
	 *
	 * @param location resource location, can be <code>null</code>
	 * @param name	 resource name, not <code>null</code>
	 * @param type,	can be <code>null</code>
	 * @param locale   resource locale, can be <code>null</code>
	 */
	protected AbstractResource(final String location, final String name, final String type, final Locale locale) {
		this.location = location;
		this.name = name;
		this.type = type;
		this.locale = locale;

		path = PathUtils.calculatePath(location, name, type, locale);
	}

	/**
	 * {@inheritDoc}
	 */
	public String getPath() {
		return path;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getType() {
		return type;
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
	public Locale getLocale() {
		return locale;
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
	public Resource getParent() {
		return location != null ? createResource(location) : null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Resource getChild(final String relativePath) {
		if (StringUtils.isEmpty(relativePath)) {
			return this;
		}
		final String suggestedPath = path + PathUtils.PATH_SEPARATOR + relativePath;
		return createResource(suggestedPath);
	}

	/**
	 * {@inheritDoc}
	 */
	public Resource inLocale(final Locale newLocale) {
		final List<Locale> locales = LocaleUtils.getCandidateLocales(newLocale);
		for (Locale candidate : locales) {
			if (candidate.equals(locale) && exists()) {
				return this;
			}
			final Resource resource = createResource(location, name, type, candidate);
			if (resource.exists()) {
				return resource;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Resource withType(final String newType) {
		if ((newType == null && type == null) || (newType != null && newType.equals(type))) {
			return this;
		}
		return createResource(location, name, newType, locale);
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
	 * Parses given absolute path to parent location, resource name, type, locale and creates new resource of needed
	 * implementation.
	 *
	 * @param absolutePath absolute resource path, not <code>null</code>
	 * @return new resource instance for specified absolute path, not <code>null</code>
	 */
	protected Resource createResource(final String absolutePath) {
		//extracting path
		final int slashIndex = absolutePath.lastIndexOf(PathUtils.PATH_SEPARATOR);
		final String suggestedPath = slashIndex < 0 ? null : absolutePath.substring(0, slashIndex);

		String resourceName = slashIndex < 0 ? absolutePath : absolutePath.substring(slashIndex + 1);

		//extracting extension
		final int dotPosition = resourceName.lastIndexOf(PathUtils.TYPE_SEPARATOR);
		final String suggestedType = dotPosition < 0 ? null : resourceName.substring(dotPosition + 1);
		if (dotPosition >= 0) {
			resourceName = resourceName.substring(0, dotPosition);
		}

		final Matcher matcher = LOCALE_PATTERN.matcher(resourceName);
		if (matcher.matches()) {
			final String suggestedName = matcher.group(1);
			final Locale suggestedLocale = LocaleUtils.parseLocale(matcher.group(2));
			return createResource(suggestedPath, suggestedName, suggestedType, suggestedLocale);
		}

		return createResource(suggestedPath, resourceName, suggestedType, LocaleUtils.ROOT_LOCALE);
	}

	/**
	 * Gets URL representation of resource. Will be <code>null</code> if resource doesn't exist.
	 *
	 * @return resource to URL representation or <code>null</code> if not exists
	 */
	protected abstract URL toURL();

	/**
	 * Creates new resource instance of needed implementation with defined location, name, type and locale.
	 *
	 * @param location resource location, can be <code>null</code>
	 * @param name	 resource name, not <code>null</code>
	 * @param type,	can be <code>null</code>
	 * @param locale   resource locale, can be <code>null</code>
	 * @return new resource instance of needed implementation
	 */
	protected abstract Resource createResource(final String location,
											   final String name,
											   final String type,
											   final Locale locale);


	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final Resource that = (Resource) o;
		return path.equals(that.getPath());

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return path.hashCode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return path;
	}
}
