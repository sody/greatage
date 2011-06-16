package org.greatage.inject.services;

import org.greatage.inject.Marker;

import java.lang.annotation.Annotation;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ServiceResources<T> {

	/**
	 * Gets service interface that both service implementation and service proxy will implement.
	 *
	 * @return service interface, not null
	 */
	Marker<T> getMarker();

	/**
	 * Gets resource to service by its type and annotations used. It can provide logger for service, injected symbols
	 * configured by {@link SymbolSource} (needs {@link org.greatage.inject.annotations.Symbol}
	 * annotation), injected services by id (needs {@link org.greatage.inject.annotations.Inject} annotation) or services by
	 * their interfaces by default.
	 *
	 * @param resourceClass resource class
	 * @param annotations   resource annotation
	 * @param <R>           resource type
	 * @return requested resource that implements specified class
	 * @throws org.greatage.inject.ApplicationException
	 *          if an error occurs instantiating resource
	 */
	<R> R getResource(Class<R> resourceClass, Annotation... annotations);
}
