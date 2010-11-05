package org.greatage.ioc.internal;

import org.greatage.ioc.*;
import org.greatage.ioc.annotations.Bind;
import org.greatage.ioc.annotations.Build;
import org.greatage.ioc.annotations.Configure;
import org.greatage.ioc.annotations.Decorate;
import org.greatage.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ModuleImpl<T> extends ServiceImpl<T> implements Module {
	private final List<Service> services = CollectionUtils.newList();
	private final List<Configurator> configurators = CollectionUtils.newList();
	private final List<Decorator> decorators = CollectionUtils.newList();

	ModuleImpl(final Class<T> moduleClass) {
		super(moduleClass.getSimpleName(), moduleClass, ScopeConstants.INTERNAL, false, true);
		services.add(this);
		for (Method method : moduleClass.getMethods()) {
			if (method.isAnnotationPresent(Build.class)) {
				services.add(new ServiceFactory(moduleClass, method));
			} else if (method.isAnnotationPresent(Configure.class)) {
				configurators.add(new ConfiguratorImpl(moduleClass, method));
			} else if (method.isAnnotationPresent(Decorate.class)) {
				decorators.add(new DecoratorImpl(moduleClass, method));
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

	public <T> List<Configurator<T>> getConfigurators(final Service<T> service) {
		final List<Configurator<T>> result = CollectionUtils.newList();
		for (Configurator configurator : configurators) {
			if (configurator.supports(service)) {
				//noinspection unchecked
				result.add(configurator);
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
}
