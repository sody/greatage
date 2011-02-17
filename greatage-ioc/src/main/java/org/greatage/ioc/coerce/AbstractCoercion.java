package org.greatage.ioc.coerce;

/**
 * This class represents abstract {@link Coercion} implementation that defines source and target classes in its
 * constructor.
 *
 * @param <S> source type
 * @param <T> target type
 * @author Ivan Khalopik
 * @since 1.1
 */
public abstract class AbstractCoercion<S, T> implements Coercion<S, T> {
	private final Class<S> sourceClass;
	private final Class<T> targetClass;

	/**
	 * Creates new coercion instance with defined source and target classes.
	 *
	 * @param sourceClass source class, not null
	 * @param targetClass target class, not null
	 */
	protected AbstractCoercion(final Class<S> sourceClass, final Class<T> targetClass) {
		assert sourceClass != null;
		assert targetClass != null;

		this.sourceClass = sourceClass;
		this.targetClass = targetClass;
	}

	/**
	 * {@inheritDoc}
	 */
	public Class<T> getTargetClass() {
		return targetClass;
	}

	/**
	 * {@inheritDoc}
	 */
	public Class<S> getSourceClass() {
		return sourceClass;
	}
}
