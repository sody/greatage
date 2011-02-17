package org.greatage.ioc.coerce;

/**
 * This interface represents {@link TypeCoercer} configuration point that provides {@link Coercion} instances by
 * supported source and target coercion classes.
 *
 * @author Ivan Khalopik
 * @since 1.1
 */
public interface CoercionProvider {

	/**
	 * Gets coercion instance by specified source and target coercion classes. If there no such coercion this method must
	 * return {@code null}.
	 *
	 * @param sourceClass source class
	 * @param targetClass target class
	 * @param <S>         source type
	 * @param <T>         target type
	 * @return coercion instance or null if not found
	 */
	<S, T> Coercion<S, T> getCoercion(Class<S> sourceClass, Class<T> targetClass);
}
