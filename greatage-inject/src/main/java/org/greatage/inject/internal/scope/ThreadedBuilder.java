package org.greatage.inject.internal.scope;

import org.greatage.inject.Marker;
import org.greatage.inject.services.ObjectBuilder;
import org.greatage.inject.services.ThreadManager;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ThreadedBuilder<T> implements ObjectBuilder<T> {
	private final ThreadManager manager;
	private final ObjectBuilder<T> builder;
	private final Marker<T> marker;

	public ThreadedBuilder(final ThreadManager manager, final Marker<T> marker, final ObjectBuilder<T> builder) {
		this.manager = manager;
		this.builder = builder;
		this.marker = marker;
	}

	public T build() {
		final T instance = manager.get(marker);
		if (instance == null) {
			final T service = builder.build();
			manager.put(marker, service);
			return service;
		}
		return instance;
	}
}
