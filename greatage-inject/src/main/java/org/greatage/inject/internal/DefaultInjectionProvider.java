package org.greatage.inject.internal;

import org.greatage.inject.Marker;
import org.greatage.inject.ServiceLocator;
import org.greatage.inject.services.InjectionProvider;

import java.lang.annotation.Annotation;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class DefaultInjectionProvider implements InjectionProvider {
	private final ServiceLocator locator;

	public DefaultInjectionProvider(final ServiceLocator locator) {
		this.locator = locator;
	}

	public <T> T inject(final Marker<?> marker, final Class<T> resourceClass, final Annotation... annotations) {
		final Marker<T> resourceMarker = InternalUtils.generateMarker(resourceClass, annotations);
		return locator.getService(resourceMarker);
	}
}