/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import org.greatage.ioc.annotations.Bind;
import org.greatage.ioc.annotations.Build;
import org.greatage.ioc.annotations.Contribute;
import org.greatage.ioc.annotations.Decorate;
import org.greatage.ioc.annotations.Intercept;
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
 * @since 1.0
 */
public class ModuleImpl<T> extends ServiceImpl<T> implements Module {
	private final List<Service> services = CollectionUtils.newList();
	private final List<Contributor> contributors = CollectionUtils.newList();
	private final List<Decorator> decorators = CollectionUtils.newList();
	private final List<Interceptor> interceptors = CollectionUtils.newList();

	/**
	 * Creates new instance of module definition for specified module class. It seeks for methods annotated with {@link
	 * Build}, {@link Contribute}, {@link Decorate}, {@link Intercept} and {@link Bind} annotations and creates for them
	 * service, contribute, decorate, intercept and bind definitions respectively.
	 *
	 * @param logger system logger
	 * @param moduleClass module class
	 */
	ModuleImpl(final Logger logger, final Class<T> moduleClass) {
		super(logger, moduleClass.getName(), moduleClass, ScopeConstants.GLOBAL, false);
		services.add(this);
		for (Method method : moduleClass.getMethods()) {
			if (method.isAnnotationPresent(Build.class)) {
				services.add(new ServiceFactory(logger, moduleClass, method));
			} else if (method.isAnnotationPresent(Contribute.class)) {
				contributors.add(new ContributorImpl(logger, moduleClass, method));
			} else if (method.isAnnotationPresent(Decorate.class)) {
				decorators.add(new DecoratorImpl(logger, moduleClass, method));
			} else if (method.isAnnotationPresent(Intercept.class)) {
				interceptors.add(new InterceptorImpl(logger, moduleClass, method));
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
	public Collection<Service> getServices() {
		return services;
	}

	/**
	 * {@inheritDoc}
	 */
	public <T> List<Contributor<T>> getContributors(final Service<T> service) {
		final List<Contributor<T>> result = CollectionUtils.newList();
		for (Contributor contributor : contributors) {
			if (contributor.supports(service)) {
				//noinspection unchecked
				result.add(contributor);
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public <T> List<Decorator<T>> getDecorators(final Service<T> service) {
		final List<Decorator<T>> result = CollectionUtils.newList();
		for (Decorator decorator : decorators) {
			if (decorator.supports(service)) {
				//noinspection unchecked
				result.add(decorator);
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public <T> List<Interceptor<T>> getInterceptors(final Service<T> service) {
		final List<Interceptor<T>> result = CollectionUtils.newList();
		for (Interceptor interceptor : interceptors) {
			if (interceptor.supports(service)) {
				//noinspection unchecked
				result.add(interceptor);
			}
		}
		return result;
	}
}
