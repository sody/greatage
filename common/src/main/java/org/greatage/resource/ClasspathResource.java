package org.greatage.resource;

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
		return classLoader.getResource(getFullName());
	}

	@Override
	protected Resource createResource(final Resource parent, final String name, final Locale locale) {
		return new ClasspathResource(classLoader, (ClasspathResource) parent, name, locale);
	}
}
