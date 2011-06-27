package org.greatage.inject.internal.scope;

import org.greatage.inject.Marker;
import org.greatage.inject.services.ProxyFactory;
import org.greatage.inject.services.Scope;
import org.greatage.inject.services.ServiceBuilder;
import org.greatage.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class IntegrationScope implements Scope {
	private final Map<Marker, Object> services = CollectionUtils.newMap();

	private final Class<? extends Annotation> key;
	private final ProxyFactory proxyFactory;

	public IntegrationScope(final Class<? extends Annotation> key, final ProxyFactory proxyFactory) {
		this.key = key;
		this.proxyFactory = proxyFactory;
	}

	public Class<? extends Annotation> getKey() {
		return key;
	}

	public <T> T get(final Marker<T> marker) {
		return marker.getServiceClass().cast(services.get(marker));
	}

	public <T> void register(final ServiceBuilder<T> builder) {
		final T proxy = proxyFactory.createProxy(builder);
		services.put(builder.getMarker(), proxy);
	}
}
