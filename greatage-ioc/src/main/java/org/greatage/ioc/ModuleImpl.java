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

import org.greatage.ioc.annotations.Bind;
import org.greatage.ioc.annotations.Build;
import org.greatage.ioc.annotations.Contribute;
import org.greatage.ioc.annotations.Decorate;
import org.greatage.ioc.logging.Logger;
import org.greatage.ioc.scope.ScopeConstants;
import org.greatage.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

/**
 * This class represents default implementation of module definition. It is based on automatically module creation by
 * its class. It can also be represented as service definition with service identifier and class like module class.
 *
 * @param <T> module service type
 * @author Ivan Khalopik
 * @since 1.1
 */
public class ModuleImpl<T> extends ServiceDefinitionImpl<T> implements Module {
	private final List<ServiceDefinition<?>> services = CollectionUtils.newList();
	private final List<ServiceContributor<?>> contributors = CollectionUtils.newList();
	private final List<ServiceDecorator<?>> decorators = CollectionUtils.newList();

	/**
	 * Creates new instance of module definition for specified module class. It seeks for methods annotated with {@link
	 * Build}, {@link Contribute}, {@link org.greatage.ioc.annotations.Decorate} and {@link Bind} annotations and creates
	 * for them service, contribute, decorate, decorate and bind definitions respectively.
	 *
	 * @param logger	  system logger
	 * @param moduleClass module class
	 */
	ModuleImpl(final Logger logger, final Class<T> moduleClass) {
		super(logger, Marker.get(moduleClass), moduleClass, ScopeConstants.GLOBAL, false, false);
		services.add(this);
		for (Method method : moduleClass.getMethods()) {
			if (method.isAnnotationPresent(Build.class)) {
				services.add(new ServiceDefinitionFactory(logger, moduleClass, method));
			} else if (method.isAnnotationPresent(Contribute.class)) {
				contributors.add(new ServiceContributorImpl(logger, moduleClass, method));
			} else if (method.isAnnotationPresent(Decorate.class)) {
				decorators.add(new ServiceDecoratorImpl(logger, moduleClass, method));
			} else if (method.isAnnotationPresent(Bind.class)) {
				final ServiceBinderImpl binder = new ServiceBinderImpl();
				try {
					method.invoke(null, binder);
					services.addAll(binder.createServices(logger));
				} catch (Exception e) {
					throw new ApplicationException("Exception in bind method", e);
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<ServiceDefinition<?>> getDefinitions() {
		return services;
	}

	/**
	 * {@inheritDoc}
	 */
	public <T> List<ServiceContributor<T>> getContributors(final Marker<T> marker) {
		final List<ServiceContributor<T>> result = CollectionUtils.newList();
		for (ServiceContributor<?> contributor : contributors) {
			if (contributor.getMarker().isAssignableFrom(marker)) {
				//noinspection unchecked
				result.add((ServiceContributor<T>) contributor);
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public <T> List<ServiceDecorator<T>> getDecorators(final Marker<T> marker) {
		final List<ServiceDecorator<T>> result = CollectionUtils.newList();
		for (ServiceDecorator<?> decorator : decorators) {
			if (decorator.getMarker().isAssignableFrom(marker)) {
				//noinspection unchecked
				result.add((ServiceDecorator<T>) decorator);
			}
		}
		return result;
	}
}
