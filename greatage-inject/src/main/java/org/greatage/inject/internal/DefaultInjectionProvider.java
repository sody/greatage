package org.greatage.inject.internal;

import org.greatage.inject.Marker;
import org.greatage.inject.services.InjectionProvider;
import org.greatage.inject.services.ScopeManager;

import java.lang.annotation.Annotation;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class DefaultInjectionProvider implements InjectionProvider {
	private final ScopeManager scopeManager;

	public DefaultInjectionProvider(final ScopeManager scopeManager) {
		this.scopeManager = scopeManager;
	}

	public <T> T inject(final Marker<?> marker, final Class<T> resourceClass, final Annotation... annotations) {
		final Marker<T> resourceMarker = InternalUtils.generateMarker(resourceClass, annotations);
		return scopeManager.get(resourceMarker);
	}
}
