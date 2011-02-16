package org.greatage.ioc.coerce;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public abstract class AbstractCoercion<S, T> implements Coercion<S, T> {
	private final Class<S> sourceClass;
	private final Class<T> targetClass;

	protected AbstractCoercion(final Class<S> sourceClass, final Class<T> targetClass) {
		this.sourceClass = sourceClass;
		this.targetClass = targetClass;
	}

	public Class<T> getTargetClass() {
		return targetClass;
	}

	public Class<S> getSourceClass() {
		return sourceClass;
	}
}
