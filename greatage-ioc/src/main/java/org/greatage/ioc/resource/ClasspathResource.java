/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
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
