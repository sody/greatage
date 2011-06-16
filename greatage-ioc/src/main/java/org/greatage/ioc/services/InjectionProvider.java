package org.greatage.ioc.services;

import org.greatage.ioc.Marker;

import java.lang.annotation.Annotation;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface InjectionProvider {

	<T> T inject(Marker<?> marker, Class<T> resourceClass, Annotation... annotations);
}
