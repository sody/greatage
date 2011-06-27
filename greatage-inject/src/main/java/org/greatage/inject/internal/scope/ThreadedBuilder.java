package org.greatage.inject.internal.scope;

import org.greatage.inject.Marker;
import org.greatage.inject.services.ServiceBuilder;
import org.greatage.inject.services.ThreadManager;

import java.lang.reflect.Method;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ThreadedBuilder<T> implements ServiceBuilder<T> {
	private final ThreadManager manager;
	private final ServiceBuilder<T> builder;
	private final Marker<T> marker;

	public ThreadedBuilder(final ThreadManager manager, final Marker<T> marker, final ServiceBuilder<T> builder) {
		this.manager = manager;
		this.builder = builder;
		this.marker = marker;
	}

	public Marker<T> getMarker() {
		return builder.getMarker();
	}

	public boolean eager() {
		return builder.eager();
	}

	public boolean intercepts(final Method method) {
		return builder.intercepts(method);
	}

	public Object invoke(final Method method, final Object... parameters) throws Throwable {
		return builder.invoke(method, parameters);
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
