package org.greatage.ioc.internal;

import org.greatage.ioc.Service;
import org.greatage.ioc.ServiceResources;
import org.greatage.ioc.services.Logger;

import java.lang.reflect.Constructor;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceImpl<T> implements Service<T> {
	private final Class<T> serviceClass;
	private final Class<? extends T> implementationClass;
	private final String serviceId;
	private final String scope;
	private final boolean override;
	private final boolean lazy;


	ServiceImpl(final String serviceId, final Class<T> serviceClass, final String scope, final boolean override, final boolean lazy) {
		this(serviceId, serviceClass, serviceClass, scope, override, lazy);
	}

	ServiceImpl(final String serviceId,
				final Class<T> serviceClass,
				final Class<? extends T> implementationClass,
				final String scope,
				final boolean override,
				final boolean lazy) {
		this.serviceClass = serviceClass;
		this.implementationClass = implementationClass;
		this.serviceId = serviceId;
		this.scope = scope;
		this.override = override;
		this.lazy = lazy;
	}

	public String getServiceId() {
		return serviceId;
	}

	public Class<T> getServiceClass() {
		return serviceClass;
	}

	public boolean isOverride() {
		return override;
	}

	public boolean isLazy() {
		return lazy;
	}

	public String getScope() {
		return scope;
	}

	public T build(final ServiceResources<T> resources) {
		final Logger logger = resources.getLogger();
		if (logger != null) {
			logger.info("Building service (%s, %s) from (%s)", serviceId, serviceClass, implementationClass);
		}

		try {
			final Constructor constructor = implementationClass.getConstructors()[0];
			final Object[] parameters = InternalUtils.calculateParameters(resources, constructor);
			return implementationClass.cast(constructor.newInstance(parameters));
		} catch (Exception e) {
			throw new RuntimeException(String.format("Can't create service of class '%s' with id '%s'", serviceClass, serviceId), e);
		}
	}
}
