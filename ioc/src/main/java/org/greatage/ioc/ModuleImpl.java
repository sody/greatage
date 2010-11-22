/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import org.greatage.ioc.annotations.*;
import org.greatage.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ModuleImpl<T> extends ServiceImpl<T> implements Module {
	private final List<Service> services = CollectionUtils.newList();
	private final List<Contributor> contributors = CollectionUtils.newList();
	private final List<Decorator> decorators = CollectionUtils.newList();
	private final List<Interceptor> interceptors = CollectionUtils.newList();

	ModuleImpl(final Class<T> moduleClass) {
		super(moduleClass.getSimpleName(), moduleClass, ScopeConstants.GLOBAL, false);
		services.add(this);
		for (Method method : moduleClass.getMethods()) {
			if (method.isAnnotationPresent(Build.class)) {
				services.add(new ServiceFactory(moduleClass, method));
			} else if (method.isAnnotationPresent(Contribute.class)) {
				contributors.add(new ContributorImpl(moduleClass, method));
			} else if (method.isAnnotationPresent(Decorate.class)) {
				decorators.add(new DecoratorImpl(moduleClass, method));
			} else if (method.isAnnotationPresent(Intercept.class)) {
				interceptors.add(new InterceptorImpl(moduleClass, method));
			} else if (method.isAnnotationPresent(Bind.class)) {
				final ServiceBinderImpl binder = new ServiceBinderImpl();
				try {
					method.invoke(null, binder);
					services.addAll(binder.getServices());
				} catch (Exception e) {
					throw new RuntimeException("Exception in bind method", e);
				}
			}
		}
	}

	public List<Service> getServices() {
		return services;
	}

	public <T> List<Contributor<T>> getConfigurators(final Service<T> service) {
		final List<Contributor<T>> result = CollectionUtils.newList();
		for (Contributor contributor : contributors) {
			if (contributor.supports(service)) {
				//noinspection unchecked
				result.add(contributor);
			}
		}
		return result;
	}

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
