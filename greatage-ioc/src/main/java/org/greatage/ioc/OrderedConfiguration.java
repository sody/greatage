/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

/**
 * This class represent ordered service configuration that is used by service contributors to configure service
 * instance. All items will be ordered by their order constraints in {@link org.greatage.util.Ordered} style. It is
 * injected to service resources as an ordered list of items at build time.
 *
 * @param <V> type of configuration items
 * @author Ivan Khalopik
 * @see org.greatage.util.OrderingUtils
 * @since 1.0
 */
public interface OrderedConfiguration<V> {

	/**
	 * Adds simple item to service configuration according to its order constrains.
	 *
	 * @param item		item instance
	 * @param orderId	 unique order id
	 * @param constraints order constraints
	 * @return this configuration instance
	 */
	OrderedConfiguration<V> add(V item, String orderId, String... constraints);

	/**
	 * Instantiates instance of specified class with all injected dependencies and adds this item to service configuration
	 * according to its order constrains.
	 *
	 * @param itemClass   item class
	 * @param orderId	 unique order id
	 * @param constraints order constraints
	 * @return this configuration instance
	 * @throws ApplicationException if error occurs while creating new instance
	 */
	OrderedConfiguration<V> addInstance(Class<? extends V> itemClass, String orderId, String... constraints);
}
