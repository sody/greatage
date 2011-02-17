package org.greatage.ioc.coerce;

/**
 * This class represents abstract {@link CoercionProvider} implementation with implemented {@link #supports(Class,
 * Class)} method.
 *
 * @author Ivan Khalopik
 * @since 1.1
 */
public abstract class AbstractCoercionProvider implements CoercionProvider {
	private final Class coercionSourceClass;
	private final Class coercionTargetClass;

	/**
	 * Creates new coercion provider that supports defined coercion source and target classes.
	 *
	 * @param coercionSourceClass source class
	 * @param coercionTargetClass target class
	 */
	protected AbstractCoercionProvider(final Class coercionSourceClass, final Class coercionTargetClass) {
		this.coercionSourceClass = coercionSourceClass;
		this.coercionTargetClass = coercionTargetClass;
	}

	/**
	 * Checks if this coercion provider instance supports coercion from source to target class.
	 *
	 * @param sourceClass source class
	 * @param targetClass target class
	 * @return true if this coercion provider instance supports coercion from source to target class, false otherwise
	 */
	protected boolean supports(final Class sourceClass, final Class targetClass) {
		if (coercionSourceClass.isAssignableFrom(sourceClass)) {
			if (targetClass.isAssignableFrom(coercionTargetClass)) {
				return true;
			}
		}
		return false;
	}
}
