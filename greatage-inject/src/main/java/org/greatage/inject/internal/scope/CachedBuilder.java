package org.greatage.inject.internal.scope;

import org.greatage.inject.Marker;
import org.greatage.inject.services.ServiceBuilder;

import java.lang.reflect.Method;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CachedBuilder<T> implements ServiceBuilder<T> {
	private final ServiceBuilder<T> builder;

	private volatile T instance;

	public CachedBuilder(final ServiceBuilder<T> builder) {
		this.builder = builder;
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

	public Object invoke(final Method method, final Object... parameters) {
		return builder.invoke(method, parameters);
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
