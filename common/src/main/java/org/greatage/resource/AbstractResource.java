/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.resource;

import org.greatage.util.I18nUtils;
import org.greatage.util.StringUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Locale;

/**
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

	protected AbstractResource(final AbstractResource parent, final String name, final Locale locale) {
		this.parent = parent;
		this.name = name;
		this.locale = locale;
	}

	public String getName() {
		return name;
	}

	public Resource getParent() {
		return parent;
	}

	public Locale getLocale() {
		return locale;
	}

	public Resource inLocale(final Locale locale) {
		final List<Locale> locales = I18nUtils.getCandidateLocales(locale);
		for (Locale candidate : locales) {
			if (candidate.equals(this.locale)) {
				return this;
			}
			final Resource resource = createResource(parent, name, locale);
			if (resource.exists()) {
				return resource;
			}
		}
		return null;
	}

	public Resource getChild(final String path) {
		if (StringUtils.isEmpty(path) || CURRENT_FOLDER.equals(path)) {
			return this;
		}
		if (PARENT_FOLDER.equals(path)) {
			return parent;
		}

		final int slashIndex = path.indexOf(FOLDER_DELIMITER);
		if (slashIndex == -1) {
			return createResource(name.lastIndexOf(FILE_DELIMITER) < 0 ? parent : this, path, locale);
		} else {
			final Resource child = getChild(path.substring(0, slashIndex));
			return child.getChild(path.substring(slashIndex + 1));
		}
	}

	public boolean exists() {
		return toURL() != null;
	}

	public InputStream open() throws IOException {
		final URL url = toURL();
		if (url == null) {
			return null;
		}
		return new BufferedInputStream(url.openStream());
	}

	protected String getFullName() {
		final StringBuilder builder = new StringBuilder();

		final String folder = parent != null ? parent.getFullName() : null;
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
			builder.append(FILE_DELIMITER).append(extension);
		}
		return builder.toString();
	}

	protected abstract URL toURL();

	protected abstract Resource createResource(final Resource parent, final String name, final Locale locale);

}
