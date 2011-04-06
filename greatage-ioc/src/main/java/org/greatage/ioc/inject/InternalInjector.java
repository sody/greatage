package org.greatage.ioc.inject;

import org.greatage.ioc.Marker;
import org.greatage.ioc.scope.InternalScope;
import org.greatage.ioc.scope.Scope;
import org.greatage.ioc.scope.ScopeManager;
import org.greatage.ioc.scope.ScopeManagerImpl;
import org.greatage.util.CollectionUtils;

import java.lang.annotation.Annotation;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class InternalInjector implements Injector {
	private final ScopeManager scopeManager;
	private final Scope scope;

	public InternalInjector() {
		scope = new InternalScope();
		scopeManager = new ScopeManagerImpl(CollectionUtils.<Scope>newSet(), scope);
	}

	public <T> T inject(final Marker<?> marker, final Class<T> resourceClass, final Annotation... annotations) {
		if (resourceClass.equals(ScopeManager.class)) {
			return resourceClass.cast(scopeManager);
		}

		if (resourceClass.equals(Scope.class)) {
			return resourceClass.cast(scope);
		}

		final Marker<T> resourceMarker = Marker.generate(resourceClass, annotations);
		return scope.get(resourceMarker);
	}
}
