package org.greatage.ioc.coerce;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public interface TypeCoercer {

	<S, T> T coerce(S input, Class<T> targetType);

}
