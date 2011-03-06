/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
