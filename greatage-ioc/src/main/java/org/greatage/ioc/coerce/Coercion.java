package org.greatage.ioc.coerce;

/**
 * This interface represents type converter that converts values from configured source class to target class. It is
 * used as configuration point for {@link TypeCoercer} service.
 *
 * @param <S> source type
 * @param <T> target type
 * @author Ivan Khalopik
 * @since 1.1
 */
public interface Coercion<S, T> {

	/**
	 * Gets configured source class.
	 *
	 * @return configured source class, not null
	 */
	Class<S> getSourceClass();

	/**
	 * Gets configured target class.
	 *
	 * @return configured target class, not null
	 */
	Class<T> getTargetClass();

	/**
	 * Converts specified source value to configured target class.
	 *
	 * @param source source value, not null
	 * @return converted to configured target class value
	 * @throws CoerceException when error occurs while converting value
	 */
	T coerce(S source);
}
