/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.util;

import java.util.List;
import java.util.Map;

/**
 * This class represent utility that orders list of ordered items according to their order id and constraints.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class OrderingUtils {

	/**
	 * Orders list of ordered items according to their order id and constraints.
	 *
	 * @param items items to order
	 * @param <T>   type of items
	 * @return list of ordered items
	 */
	public static <T extends Ordered> List<T> order(final List<T> items) {
		// create item nodes mapped by their order id
		final Map<String, OrderedNode<T>> nodes = CollectionUtils.newMap();
		for (T item : items) {
			// throw an exception if item with such order id is already specified
			if (nodes.containsKey(item.getOrderId())) {
				throw new IllegalArgumentException("Ordered identifier must be unique.");
			}
			nodes.put(item.getOrderId(), new OrderedNode<T>(item));
		}

		// add dependencies to item nodes according to their order constraints
		for (T item : items) {
			final OrderedNode<T> source = nodes.get(item.getOrderId());
			for (String constraint : item.getOrderConstraints()) {
				if (constraint.startsWith(Ordered.BEFORE)) {
					// if constraint like before:targetId add current item to dependencies of target item
					final String targetId = constraint.substring(Ordered.BEFORE.length());
					final OrderedNode<T> target = nodes.get(targetId);
					// throw exception if target item is not exists
					if (target == null) {
						throw new IllegalArgumentException(String.format("Ordered item with id '%s' not found. It is dependency of item with id '%s'", targetId, item.getOrderId()));
					}
					// throw exception for cycle dependencies
					if (source.isReachable(target)) {
						throw new IllegalArgumentException(String.format("Cycle dependency of items with id '%s' and '%s'", targetId, item.getOrderId()));
					}
					target.addDependency(source);
				} else if (constraint.startsWith(Ordered.AFTER)) {
					// if constraint like after:targetId add target item to dependencies of current item
					final String targetId = constraint.substring(Ordered.AFTER.length());
					final OrderedNode<T> target = nodes.get(targetId);
					// throw exception if target item is not exists
					if (target == null) {
						throw new IllegalArgumentException(String.format("Ordered item with id '%s' not found. It is dependency of item with id '%s'", targetId, item.getOrderId()));
					}
					// throw exception for cycle dependencies
					if (target.isReachable(source)) {
						throw new IllegalArgumentException(String.format("Cycle dependency of items with id '%s' and '%s'", targetId, item.getOrderId()));
					}
					source.addDependency(target);
				} else {
					// throw exception for wrong constraint name
					throw new IllegalArgumentException("Unknown order constraint");
				}
			}
		}

		//fill resulting list with items from all nodes and their dependencies
		final List<T> result = CollectionUtils.newList();
		for (OrderedNode<T> node : nodes.values()) {
			node.fill(result);
		}
		return result;
	}

}

class OrderedNode<T extends Ordered> {
	private final T item;
	private final List<OrderedNode<T>> dependencies = CollectionUtils.newList();

	OrderedNode(final T item) {
		this.item = item;
	}

	void addDependency(final OrderedNode<T> node) {
		dependencies.add(node);
	}

	boolean isReachable(final OrderedNode<T> node) {
		if (equals(node)) {
			return true;
		}
		for (OrderedNode<T> dependency : dependencies) {
			if (dependency.isReachable(node)) {
				return true;
			}
		}
		return false;
	}

	void fill(final List<T> items) {
		if (!items.contains(item)) {
			for (OrderedNode<T> dependency : dependencies) {
				dependency.fill(items);
			}
			items.add(item);
		}
	}
}
