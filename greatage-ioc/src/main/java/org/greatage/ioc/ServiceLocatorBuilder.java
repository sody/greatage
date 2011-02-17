/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import org.greatage.ioc.annotations.Dependency;
import org.greatage.util.CollectionUtils;
import org.greatage.util.Locker;

import java.util.List;

/**
 * This class represents utility that simplifies {@link ServiceLocator} building process.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceLocatorBuilder {
	private final List<Module> modules = CollectionUtils.newList();
	private final Locker locker = new Locker();

	/**
	 * Creates new service locator builder with defined {@link IOCModule} core module.
	 */
	public ServiceLocatorBuilder() {
		addModule(IOCModule.class);
	}

	/**
	 * Adds module instances to service locator builder.
	 *
	 * @param moduleInstances module instances
	 * @return this instance
	 */
	public ServiceLocatorBuilder addModules(final Module... moduleInstances) {
		locker.check();
		for (Module module : moduleInstances) {
			addModule(module);
		}
		return this;
	}

	/**
	 * Adds module classes to service locator builder.
	 *
	 * @param moduleClasses module classes
	 * @return this instance
	 */
	public ServiceLocatorBuilder addModules(final Class<?>... moduleClasses) {
		locker.check();
		for (Class<?> moduleClass : moduleClasses) {
			addModule(moduleClass);
		}
		return this;
	}

	/**
	 * Adds module instance to service locator builder.
	 *
	 * @param moduleInstance module instance
	 * @return this instance
	 */
	public ServiceLocatorBuilder addModule(final Module moduleInstance) {
		locker.check();
		assert moduleInstance != null;

		modules.add(moduleInstance);
		return this;
	}

	/**
	 * Adds module class to service locator builder.
	 *
	 * @param <T>         module type
	 * @param moduleClass module class
	 * @return this instance
	 */
	public <T> ServiceLocatorBuilder addModule(final Class<T> moduleClass) {
		locker.check();
		assert moduleClass != null;

		// if module class is annotated with Dependency annotation, adds specified module classes
		final Dependency dependency = moduleClass.getAnnotation(Dependency.class);
		if (dependency != null) {
			addModules(dependency.value());
		}
		addModule(new ModuleImpl<T>(moduleClass));
		return this;
	}

	/**
	 * Builds new service locator instance with all collected modules defined.
	 *
	 * @return new service locator instance
	 */
	public ServiceLocator build() {
		locker.lock();
		return new ServiceLocatorImpl(modules);
	}

	/**
	 * Creates new service locator instance for specified module classes + IOCModule.
	 *
	 * @param moduleClasses module classes
	 * @return new service locator instance
	 */
	public static ServiceLocator createServiceLocator(final Class... moduleClasses) {
		final ServiceLocatorBuilder builder = new ServiceLocatorBuilder().addModules(moduleClasses);
		return builder.build();
	}
}