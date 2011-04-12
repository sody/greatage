package org.greatage.ioc.inject;

import org.greatage.ioc.Marker;
import org.greatage.ioc.ServiceLocator;

import java.lang.annotation.Annotation;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class DefaultInjectionProvider implements InjectionProvider {
	private final ServiceLocator locator;

	public DefaultInjectionProvider(final ServiceLocator locator) {
		this.locator = locator;
	}

	public <T> T inject(final Marker<?> marker, final Class<T> resourceClass, final Annotation... annotations) {
		final Marker<T> resourceMarker = Marker.generate(resourceClass, annotations);
		return locator.getService(resourceMarker);
	}
}
