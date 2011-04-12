package org.greatage.ioc;

import org.greatage.ioc.annotations.Dependency;
import org.greatage.ioc.logging.Logger;
import org.greatage.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class ServiceLocatorModule implements Module, ServiceContributor<ServiceLocator> {
	private final Marker<ServiceLocator> marker = Marker.get(ServiceLocator.class);
	private final List<Module> modules = CollectionUtils.newList();

	private final Logger logger;

	ServiceLocatorModule(final Logger logger) {
		this.logger = logger;

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
		addModule(new ModuleImpl(logger, moduleClass));
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

	public String getOrderId() {
		return "";
	}

	public List<String> getOrderConstraints() {
		return CollectionUtils.newList();
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

	public <T> List<ServiceDecorator<T>> getDecorators(final Marker<T> marker) {
		return Collections.emptyList();
	}
}
