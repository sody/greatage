/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import org.greatage.util.CollectionUtils;
import org.greatage.util.Ordered;
import org.greatage.util.OrderingUtils;

import java.util.Arrays;
import java.util.List;

/**
 * This class represents default implementation of ordered service configuration. The resulting type of such
 * configuration is {@link List}.
 *
 * @param <T> service type
 * @param <V> type of configuration items
 * @author Ivan Khalopik
 * @since 1.0
 */
public class OrderedConfigurationImpl<T, V> extends AbstractConfiguration<T, V, List<V>>
		implements OrderedConfiguration<V> {
	private final List<OrderedItem> configuration = CollectionUtils.newList();

	/**
	 * Creates new instance of ordered service configuration with defined service resources.
	 *
	 * @param resources service resource
	 */
	OrderedConfigurationImpl(final ServiceResources<T> resources) {
		super(resources);
	}

	/**
	 * {@inheritDoc}
	 */
	public OrderedConfiguration<V> add(final V item, final String id, final String... constraints) {
		configuration.add(new OrderedItem(item, id, constraints));
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public OrderedConfiguration<V> addInstance(final Class<? extends V> clazz, final String id,
											   final String... constraints) {
		add(newInstance(clazz), id, constraints);
		return this;
	}

	/**
	 * Creates resulting instance of ordered service configuration as ordered list of configuration items.
	 *
	 * @return resulting ordered list of configuration items
	 */
	public List<V> build() {
		final List<V> result = CollectionUtils.newList();

		final List<OrderedItem> orderedItems = OrderingUtils.order(configuration);
		for (OrderedItem orderedItem : orderedItems) {
			result.add(orderedItem.getItem());
		}
		return result;
	}

	class OrderedItem implements Ordered {
		private final String orderId;
		private final List<String> orderConstraints;
		private final V item;

		OrderedItem(final V item, final String orderId, final String... orderConstraints) {
			this.item = item;
			this.orderId = orderId;
			this.orderConstraints = Arrays.asList(orderConstraints);
		}

		public V getItem() {
			return item;
		}

		public String getOrderId() {
			return orderId;
		}

		public List<String> getOrderConstraints() {
			return orderConstraints;
		}
	}
}
