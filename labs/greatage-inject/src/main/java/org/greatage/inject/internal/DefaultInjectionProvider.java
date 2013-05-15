package org.greatage.inject.internal;

import org.greatage.inject.ApplicationException;
import org.greatage.inject.Marker;
import org.greatage.inject.services.InjectionProvider;
import org.greatage.inject.services.ScopeManager;

import java.lang.annotation.Annotation;
import java.util.Set;

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
		final Set<T> services = scopeManager.find(resourceMarker);
		if (services.size() > 1) {
			throw new ApplicationException(
					String.format("Can't find service (%s). More than one service available", marker));
		}
		if (services.size() < 1) {
			throw new ApplicationException(String.format("Can't find service (%s) for (%s)", resourceMarker, marker));
		}
		return services.iterator().next();
	}
}
