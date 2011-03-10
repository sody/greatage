/*
 * Copyright 2011 Ivan Khalopik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.greatage.ioc;

import org.greatage.ioc.proxy.Interceptor;
import org.greatage.ioc.proxy.ObjectBuilder;
import org.greatage.ioc.proxy.ProxyFactory;
import org.greatage.util.DescriptionBuilder;
import org.greatage.util.Locker;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents implementation of {@link ServiceStatus} that is used by default for all services. It lazily
 * creates, configures, decorates and intercepts service using {@link ProxyFactory} service and scoped builder.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceStatusImpl<T> implements ServiceStatus<T> {
	private final ServiceResources<T> resources;
	private final ObjectBuilder<T> builder;
	private final List<Decorator<T>> decorators;

	private final Locker locker = new Locker();

	private T serviceInstance;

	/**
	 * Creates new instance of service status that is used by default for all services. It lazily creates, configures,
	 * decorates and intercepts service using {@link ProxyFactory} service and scoped builder.
	 *
	 * @param locator	  service locator
	 * @param service	  service definition
	 * @param contributors service contributors
	 * @param decorators   service decorators
	 */
	ServiceStatusImpl(final ServiceLocator locator,
					  final Service<T> service,
					  final List<Contributor<T>> contributors,
					  final List<Decorator<T>> decorators) {
		this.resources = new ServiceInitialResources<T>(locator, service);
		final ServiceBuilder<T> serviceBuilder = new ServiceBuilder<T>(resources, service, contributors);
		this.builder = new ScopedBuilder<T>(resources, serviceBuilder);
		this.decorators = decorators;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getServiceId() {
		return resources.getServiceId();
	}

	/**
	 * {@inheritDoc}
	 */
	public Class<T> getServiceClass() {
		return resources.getServiceClass();
	}

	/**
	 * {@inheritDoc}
	 */
	public String getServiceScope() {
		return resources.getServiceScope();
	}

	/**
	 * {@inheritDoc} Creates service instance using {@link ProxyFactory} service and scoped service builder.
	 */
	public T getService() {
		if (serviceInstance == null) {
			locker.lock();
			final ProxyFactory proxyFactory = resources.getResource(ProxyFactory.class);
			serviceInstance = proxyFactory.createProxy(builder, createInterceptors());
		}
		return serviceInstance;
	}

	/**
	 * Creates ordered list of method advices for service using service interceptor definitions.
	 *
	 * @return list of method advices for service or empty list
	 */
	private List<Interceptor> createInterceptors() {
		final List<Interceptor> interceptors = new ArrayList<Interceptor>();
		for (Decorator<T> decorator : decorators) {
			final Interceptor interceptor = decorator.decorate(resources);
			interceptors.add(interceptor);
		}
		return interceptors;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final DescriptionBuilder db = new DescriptionBuilder(getClass());
		db.append("resources", resources);
		db.append("builder", builder);
		return db.toString();
	}
}
