package org.greatage.ioc.coerce;

import java.util.Collection;

/**
 * This class represents {@link CoercionProvider} that can be configured with multiply {@link Coercion} instances.
 *
 * @author Ivan Khalopik
 * @since 1.1
 */
public class DefaultCoercionProvider implements CoercionProvider {
	private final Collection<Coercion> coercions;

	/**
	 * Creates new coercion provider configured with multiply {@link Coercion} instances.
	 *
	 * @param coercions coercions configuration
	 */
	public DefaultCoercionProvider(final Collection<Coercion> coercions) {
		assert coercions != null;

		this.coercions = coercions;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public <S, T> Coercion<S, T> getCoercion(final Class<S> sourceClass, final Class<T> targetClass) {
		for (Coercion coercion : coercions) {
			if (supports(coercion, sourceClass, targetClass)) {
				return coercion;
			}
		}
		return null;
	}

	/**
	 * Checks if specified coercion instance supports coercion from source to target class.
	 *
	 * @param coercion	coercion to check
	 * @param sourceClass source class
	 * @param targetClass target class
	 * @return true if specified coercion instance supports coercion from source to target class, false otherwise
	 */
	private boolean supports(final Coercion coercion, final Class sourceClass, final Class targetClass) {
		if (coercion.getSourceClass().isAssignableFrom(sourceClass)) {
			if (targetClass.isAssignableFrom(coercion.getTargetClass())) {
				return true;
			}
		}
		return false;
	}
}
