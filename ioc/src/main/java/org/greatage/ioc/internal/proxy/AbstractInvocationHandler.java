package org.greatage.ioc.internal.proxy;

import org.greatage.ioc.services.ObjectBuilder;
import org.greatage.util.DescriptionBuilder;
import org.greatage.util.Locker;

/**
 * This class represents utility for lazy creation of object from object builder.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractInvocationHandler<T> {
	private final ObjectBuilder<T> builder;
	private final Locker locker = new Locker();
	private T delegate;

	/**
	 * Creates new instance of utility for lazy creation of object from specified object builder.
	 *
	 * @param builder object builder
	 */
	protected AbstractInvocationHandler(final ObjectBuilder<T> builder) {
		this.builder = builder;
	}

	/**
	 * Gets object instance. If instance is not initialized, creates it from object builder.
	 *
	 * @return lazy initialized object instance
	 */
	protected T getDelegate() {
		if (delegate == null) {
			// locks lazy creation to prevent multy building
			locker.lock();
			delegate = builder.build();
		}
		return delegate;
	}

	@Override
	public String toString() {
		final DescriptionBuilder db = new DescriptionBuilder(getClass());
		db.append("builder", builder);
		db.append("delegate", delegate);
		return db.toString();
	}
}
