/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

/**
 * This class represent mapped service configuration that is used by service contributors to configure service instance.
 * It is injected to service resources as a map of keyed items at build time.
 *
 * @param <K> type of configuration keys
 * @param <V> type of configuration values
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface MappedConfiguration<K, V> {

	/**
	 * Adds simple keyed item to service configuration.
	 *
	 * @param key   item key
	 * @param value item instance
	 * @return this configuration instance
	 */
	MappedConfiguration<K, V> add(K key, V value);

	/**
	 * Instantiates instance of specified class with all injected dependencies and adds this keyed item to service
	 * configuration.
	 *
	 * @param key		item key
	 * @param valueClass item class
	 * @return this configuration instance
	 * @throws ApplicationException if error occurs while creating new instance
	 */
	MappedConfiguration<K, V> addInstance(K key, Class<? extends V> valueClass);
}
