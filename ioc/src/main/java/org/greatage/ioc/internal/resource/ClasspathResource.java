/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.internal.resource;

import org.greatage.ioc.services.Resource;

import java.net.URL;
import java.util.Locale;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ClasspathResource extends AbstractResource {
	private final ClassLoader classLoader;

	public ClasspathResource(final ClassLoader classLoader, final ClasspathResource parent, final String name, final Locale locale) {
		super(parent, name, locale);
		this.classLoader = classLoader;
	}

	@Override
	protected URL toURL() {
		final String path = getPath();
		return classLoader.getResource(path);
	}

	@Override
	protected Resource createResource(final Resource parent, final String name, final Locale locale) {
		return new ClasspathResource(classLoader, (ClasspathResource) parent, name, locale);
	}
}
