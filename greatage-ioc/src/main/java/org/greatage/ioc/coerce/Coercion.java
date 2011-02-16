package org.greatage.ioc.coerce;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public interface Coercion<S, T> {

	Class<S> getSourceClass();

	Class<T> getTargetClass();

	T coerce(S source);
}
