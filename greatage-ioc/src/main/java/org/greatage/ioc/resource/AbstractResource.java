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
import org.greatage.util.LocaleUtils;
import org.greatage.util.PathSearcher;
import org.greatage.util.PathUtils;
import org.greatage.util.StringUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents abstract {@link Resource} implementation that implements all base resource logic. All {@link Resource}
 * implementation should be subclasses from it to be completely processed with {@link ResourceLocator} service.
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
	 * Creates new instance of resource with defined location, name, type and locale. It also will calculate resource path using
	 * {@link PathUtils} utility.
	 *
	 * @param location resource location, can be <code>null</code>
	 * @param name resource name, not <code>null</code>
	 * @param type resource type, can be <code>null</code>
	 * @param locale resource locale, can be <code>null</code>
	 */
	protected AbstractResource(final String location, final String name, final String type, final Locale locale) {
		assert name != null : "Resource name should not be null";

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
	public Resource parent() {
		return location != null ? create(location) : null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Resource create(final String absolutePath) {
		// process root path
		if (absolutePath.equals(PathUtils.PATH_SEPARATOR)) {
			return createResource(null, absolutePath, null, null);
		}

		// cut ending slash
		String resourceName = absolutePath.replaceAll("/+$", "");

		// calculate location
		final int slashIndex = resourceName.lastIndexOf(PathUtils.PATH_SEPARATOR);
		final String suggestedLocation;
		if (slashIndex >= 0) {
			suggestedLocation = slashIndex == 0 ? PathUtils.PATH_SEPARATOR : resourceName.substring(0, slashIndex);
			resourceName = resourceName.substring(slashIndex + 1);
		} else {
			suggestedLocation = null;
		}

		// calculate extension
		final int dotPosition = resourceName.lastIndexOf(PathUtils.TYPE_SEPARATOR);
		final String suggestedType;
		if (dotPosition >= 0) {
			suggestedType = (dotPosition == resourceName.length() - 1) ? null : resourceName.substring(dotPosition + 1);
			resourceName = resourceName.substring(0, dotPosition);
		} else {
			suggestedType = null;
		}

		// calculate locale
		final Locale suggestedLocale;
		final Matcher matcher = LOCALE_PATTERN.matcher(resourceName);
		if (matcher.matches()) {
			suggestedLocale = LocaleUtils.parseLocale(matcher.group(2));
			resourceName = matcher.group(1);
		} else {
			suggestedLocale = null;
		}

		// create new resource
		return createResource(suggestedLocation, resourceName, suggestedType, suggestedLocale);
	}

	/**
	 * {@inheritDoc}
	 */
	public Resource child(final String relativePath) {
		assert relativePath != null : "Path should not be null";
		assert !relativePath.startsWith(PathUtils.PATH_SEPARATOR) : "Path should be relative";

		if (StringUtils.isEmpty(relativePath)) {
			return this;
		}
		final String suggestedPath = StringUtils.isEmpty(path) ? relativePath :
				path.equals(PathUtils.PATH_SEPARATOR) ?
						path + relativePath :
						path + PathUtils.PATH_SEPARATOR + relativePath;
		return create(suggestedPath);
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<Resource> children(final String... includes) {
		final Set<String> includeSet = CollectionUtils.newSet(includes);
		return children(includeSet, null);
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<Resource> children(final Set<String> includes, final Set<String> excludes) {
		final Set<Resource> children = CollectionUtils.newSet();

		if (exists()) {
			final URL url = toURL();
			if (url != null) {
				addFiles(children, url.toExternalForm(), includes, excludes);
			}
		}
		return children;
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

	protected void addFiles(final Set<Resource> resources,
							final String absolutePath,
							final Set<String> includes,
							final Set<String> excludes) {
		final Set<String> resourceNames = PathSearcher.create(absolutePath).include(includes).exclude(excludes).search();

		final StringBuilder pathBuilder = new StringBuilder();
		if (!StringUtils.isEmpty(absolutePath)) {
			pathBuilder.append(absolutePath);
			if (!absolutePath.endsWith(PathUtils.PATH_SEPARATOR)) {
				pathBuilder.append(PathUtils.PATH_SEPARATOR);
			}
		}

		final String prefix = pathBuilder.toString();
		for (String resourceName : resourceNames) {
			final Resource resource = URIResource.get(prefix + resourceName);
			if (resource != null && resource.exists()) {
				resources.add(resource);
			}
		}
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
	 * @param name resource name, not <code>null</code>
	 * @param type resource type, can be <code>null</code>
	 * @param locale resource locale, can be <code>null</code>
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
