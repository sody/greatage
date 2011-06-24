package org.greatage.inject.internal.scope;

import org.greatage.inject.services.ObjectBuilder;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CachedBuilder<T> implements ObjectBuilder<T> {
	private final ObjectBuilder<T> builder;

	private volatile T instance;

	public CachedBuilder(final ObjectBuilder<T> builder) {
		this.builder = builder;
	}

	public T build() {
		if (instance == null) {
			synchronized (CachedBuilder.class) {
				if (instance == null) {
					instance = builder.build();
				}
			}
		}
		return instance;
	}
}
