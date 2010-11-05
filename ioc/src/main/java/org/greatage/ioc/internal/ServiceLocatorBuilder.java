package org.greatage.ioc.internal;

import org.greatage.ioc.IOCModule;
import org.greatage.ioc.Module;
import org.greatage.ioc.ServiceLocator;
import org.greatage.ioc.annotations.Dependency;
import org.greatage.util.CollectionUtils;
import org.greatage.util.Locker;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceLocatorBuilder {
	private final List<Module> modules = CollectionUtils.newList();
	private final Locker locker = new Locker();

	public ServiceLocatorBuilder() {
		addModule(IOCModule.class);
	}

	public ServiceLocatorBuilder addModules(final Module... modules) {
		locker.check();
		for (Module module : modules) {
			addModule(module);
		}
		return this;
	}

	public ServiceLocatorBuilder addModules(final Class<?>... moduleClasses) {
		locker.check();
		for (Class<?> moduleClass : moduleClasses) {
			addModule(moduleClass);
		}
		return this;
	}

	public ServiceLocatorBuilder addModule(final Module module) {
		locker.check();
		assert module != null;

		modules.add(module);
		return this;
	}

	public <T> ServiceLocatorBuilder addModule(final Class<T> moduleClass) {
		locker.check();
		assert moduleClass != null;

		final Dependency dependency = moduleClass.getAnnotation(Dependency.class);
		if (dependency != null) {
			addModules(dependency.value());
		}
		addModule(new ModuleImpl<T>(moduleClass));
		return this;
	}

	public ServiceLocator build() {
		locker.lock();
		return new ServiceLocatorImpl(modules);
	}

	public static ServiceLocator createServiceLocator(final Class... moduleClasses) {
		final ServiceLocatorBuilder builder = new ServiceLocatorBuilder().addModules(moduleClasses);
		return builder.build();
	}
}
