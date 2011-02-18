package org.greatage.ioc.coerce;

/**
 * This interface represents type coercer service that converts source values to specified target class.
 *
 * @author Ivan Khalopik
 * @since 1.1
 */
public interface TypeCoercer {

	/**
	 * Converts source value to specified target class.
	 *
	 * @param input	  source value
	 * @param targetType target class
	 * @param <S>        source type
	 * @param <T>        target type
	 * @return converted source value
	 * @throws CoerceException if can not convert
	 */
	<S, T> T coerce(S input, Class<T> targetType);

	/**
	 * Gets coercion instance that supports coercion from source to target class.
	 *
	 * @param sourceClass source class
	 * @param targetClass target class
	 * @param <S>         source type
	 * @param <T>         target type
	 * @return coercion instance or null if no correspondent coercion found
	 */
	<S, T> Coercion<S, T> getCoercion(Class<S> sourceClass, Class<T> targetClass);
}
