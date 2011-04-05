/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
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

import org.greatage.ioc.inject.Injector;
import org.greatage.ioc.proxy.Interceptor;
import org.greatage.ioc.proxy.ObjectBuilder;
import org.greatage.ioc.proxy.ProxyFactory;
import org.greatage.ioc.scope.Scope;
import org.greatage.ioc.scope.ScopeManager;
import org.greatage.util.CollectionUtils;
import org.greatage.util.DescriptionBuilder;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * This class represents implementation of {@link ServiceProvider} that is used by default for all services. It lazily creates,
 * configures, decorates and intercepts service using {@link ProxyFactory} service and scoped builder.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.1
 */
public class ServiceProviderImpl<T> implements ServiceProvider<T>, ServiceResources<T>, ObjectBuilder<T> {
	private final Injector injector;
	private final ServiceDefinition<T> service;
	private final List<ServiceContributor<T>> contributors;
	private final List<ServiceDecorator<T>> decorators;

	private Scope scope;

	/**
	 * Creates new instance of service status that is used by default for all services. It lazily creates, configures, decorates and
	 * intercepts service using {@link ProxyFactory} service and scoped builder.
	 *
	 * @param service	  service definition
	 * @param contributors service contributors
	 * @param decorators   service decorators
	 */
	ServiceProviderImpl(final Injector injector,
						final ServiceDefinition<T> service,
						final List<ServiceContributor<T>> contributors,
						final List<ServiceDecorator<T>> decorators) {
		this.injector = injector;
		this.service = service;
		this.contributors = contributors;
		this.decorators = decorators;

		final ScopeManager scopeManager = injector.inject(service.getMarker(), ScopeManager.class);
		scope = scopeManager.getScope(service.getScope());
		scope.put(getMarker(), this);
	}

	/**
	 * {@inheritDoc}
	 */
	public Marker<T> getMarker() {
		return service.getMarker();
	}

	public <R> R getResource(final Class<R> resourceClass, final Annotation... annotations) {
		return injector.inject(getMarker(), resourceClass, annotations);
	}

	public Class<T> getObjectClass() {
		return getMarker().getServiceClass();
	}

	public List<Interceptor> getInterceptors() {
		final List<Interceptor> interceptors = CollectionUtils.newList();
		for (ServiceDecorator<T> decorator : decorators) {
			decorator.decorate(this);
		}
		return interceptors;
	}

	public T build() {
		final ServiceResources<T> buildResources = new BuildResources<T>(this, contributors);
		return service.build(buildResources);
	}

	/**
	 * {@inheritDoc}
	 */
	public Scope getScope() {
		return scope;
	}

	/**
	 * {@inheritDoc} Creates service instance using {@link ProxyFactory} service and scoped service builder.
	 */
	public T getService() {
		return scope.get(getMarker());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final DescriptionBuilder db = new DescriptionBuilder(getClass());
		db.append("injector", injector);
		db.append("service", service);
		return db.toString();
	}
}
