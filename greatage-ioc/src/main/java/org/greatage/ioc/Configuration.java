/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

/**
 * This class represent unordered service configuration that is used by service contributors to configure service
 * instance. It is injected to service resources as a collection of items at build time.
 *
 * @param <V> type of configuration items
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Configuration<V> {

	/**
	 * Adds simple item to service configuration.
	 *
	 * @param item item instance
	 * @return this configuration instance
	 */
	Configuration<V> add(V item);

	/**
	 * Instantiates instance of specified class with all injected dependencies and adds this item to service
	 * configuration.
	 *
	 * @param itemClass item class
	 * @return this configuration instance
	 */
	Configuration<V> addInstance(Class<? extends V> itemClass);
}
