/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import org.greatage.util.Locker;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * This class represents service resources proxy that is used during service build phase and adds resulting service
 * configuration as additional resource. This resources are calculated with service contributors during configure
 * phase.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceBuildResources<T> extends ServiceAdditionalResources<T> {
	private final List<Contributor<T>> contributors;
	private final Locker locker = new Locker();

	/**
	 * Creates new instance of service resources proxy for build phase with defined service resources delegate and list of
	 * service contributors.
	 *
	 * @param delegate	 service resources delegate
	 * @param contributors service contributors
	 */
	ServiceBuildResources(final ServiceResources<T> delegate, final List<Contributor<T>> contributors) {
		super(delegate);
		this.contributors = contributors;
	}

	/**
	 * {@inheritDoc} Retrieves correspondent service configuration.
	 *
	 * @return correspondent service configuration or null
	 */
	@Override
	public <E> E getAdditionalResource(final Class<E> resourceClass) {
		if (Map.class.equals(resourceClass)) {
			locker.lock();
			return resourceClass.cast(getMappedConfiguration());
		}
		if (List.class.equals(resourceClass)) {
			locker.lock();
			return resourceClass.cast(getOrderedConfiguration());
		}
		if (Collection.class.equals(resourceClass)) {
			locker.lock();
			return resourceClass.cast(getConfiguration());
		}
		return null;
	}

	/**
	 * Creates mapped service configuration contributed by all configured service contributors.
	 *
	 * @param <K> type of configuration keys
	 * @param <V> type of configuration values
	 * @return map as service mapped configuration, not null
	 */
	private <K, V> Map<K, V> getMappedConfiguration() {
		final MappedConfigurationImpl<T, K, V> configuration = new MappedConfigurationImpl<T, K, V>(getDelegate());
		configure(configuration);
		return configuration.build();
	}

	/**
	 * Creates ordered service configuration contributed by all configured service contributors.
	 *
	 * @param <V> type of configuration items
	 * @return list as service ordered configuration, not null
	 */
	private <V> List<V> getOrderedConfiguration() {
		final OrderedConfigurationImpl<T, V> configuration = new OrderedConfigurationImpl<T, V>(getDelegate());
		configure(configuration);
		return configuration.build();
	}

	/**
	 * Creates unordered service configuration contributed by all configured service contributors.
	 *
	 * @param <V> type of configuration items
	 * @return collection as service unordered configuration, not null
	 */
	private <V> Collection<V> getConfiguration() {
		final ConfigurationImpl<T, V> configuration = new ConfigurationImpl<T, V>(getDelegate());
		configure(configuration);
		return configuration.build();
	}

	/**
	 * Contributes service configuration by triggering configure phase.
	 *
	 * @param configuration correspondent service configuration instance
	 */
	private void configure(final Object configuration) {
		final ServiceConfigureResources<T> resources = new ServiceConfigureResources<T>(getDelegate(), configuration);
		for (Contributor<T> contributor : contributors) {
			contributor.contribute(resources);
		}
	}
}
