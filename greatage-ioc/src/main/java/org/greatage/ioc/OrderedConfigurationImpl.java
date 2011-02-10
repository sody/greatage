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
 * @author Ivan Khalopik
 * @since 1.0
 */
public class OrderedConfigurationImpl<T, V> extends AbstractConfiguration<T, V, List<V>> implements OrderedConfiguration<V> {
	private final List<OrderedItem> configuration = CollectionUtils.newList();

	OrderedConfigurationImpl(final ServiceResources<T> resources) {
		super(resources);
	}

	public OrderedConfiguration<V> add(final V item, final String id, final String... constraints) {
		configuration.add(new OrderedItem(item, id, constraints));
		return this;
	}

	public OrderedConfiguration<V> addInstance(final Class<? extends V> clazz, final String id, final String... constraints) {
		add(newInstance(clazz), id, constraints);
		return this;
	}

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
