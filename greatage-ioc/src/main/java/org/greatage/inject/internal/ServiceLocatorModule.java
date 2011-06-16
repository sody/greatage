package org.greatage.inject.internal;

import org.greatage.inject.Configuration;
import org.greatage.inject.IOCModule;
import org.greatage.inject.Key;
import org.greatage.inject.Marker;
import org.greatage.inject.ServiceLocator;
import org.greatage.inject.annotations.Dependency;
import org.greatage.inject.services.Module;
import org.greatage.inject.services.ServiceContributor;
import org.greatage.inject.services.ServiceDefinition;
import org.greatage.inject.services.ServiceInterceptor;
import org.greatage.inject.services.ServiceResources;
import org.greatage.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceLocatorModule implements Module, ServiceContributor<ServiceLocator> {
	private final Marker<ServiceLocator> marker = Key.get(ServiceLocator.class);
	private final List<Module> modules = CollectionUtils.newList();

	ServiceLocatorModule() {
		addModule(this);
		addModule(IOCModule.class);
	}

	void addModule(final Module moduleInstance) {
		modules.add(moduleInstance);
	}

	void addModules(final Module... moduleInstances) {
		modules.addAll(Arrays.asList(moduleInstances));
	}

	void addModule(final Class<?> moduleClass) {
		final Dependency dependency = moduleClass.getAnnotation(Dependency.class);
		if (dependency != null) {
			addModules(dependency.value());
		}
		//noinspection unchecked
		addModule(new ModuleImpl(moduleClass));
	}

	void addModules(final Class... moduleClasses) {
		for (Class moduleClass : moduleClasses) {
			addModule(moduleClass);
		}
	}

	Collection<Module> getModules() {
		return modules;
	}

	public Marker<ServiceLocator> getMarker() {
		return marker;
	}

	public void contribute(final ServiceResources<ServiceLocator> resources) {
		@SuppressWarnings("unchecked")
		final Configuration<Module> configuration = resources.getResource(Configuration.class);

		for (Module module : modules) {
			configuration.add(module);
		}
	}

	public Collection<ServiceDefinition<?>> getDefinitions() {
		return Collections.emptyList();
	}

	public <T> List<ServiceContributor<T>> getContributors(final Marker<T> marker) {
		final List<ServiceContributor<T>> contributors = CollectionUtils.newList();
		if (this.marker.isAssignableFrom(marker)) {
			//noinspection unchecked
			contributors.add((ServiceContributor<T>) this);
		}
		return contributors;
	}

	public <T> List<ServiceInterceptor<T>> getInterceptors(final Marker<T> marker) {
		return Collections.emptyList();
	}
}
