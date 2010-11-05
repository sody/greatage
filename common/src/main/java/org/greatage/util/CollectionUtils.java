/*
 * Copyright 2000 - 2009 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * This class represents utility methods for working with java arrays and collections.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class CollectionUtils {

	/**
	 * Checks if collection is empty.
	 *
	 * @param collection collection to check
	 * @return true if collection is not null and has at last one element, false otherwise
	 */
	public static boolean isEmpty(Collection collection) {
		return (collection == null || collection.isEmpty());
	}

	/**
	 * Creates new empty generic set.
	 *
	 * @param <V> type of values
	 * @return new empty set
	 */
	public static <V> Set<V> newSet() {
		return new HashSet<V>();
	}

	/**
	 * Creates new generic set initialized with specified values.
	 *
	 * @param <V>    type of values
	 * @param values values to initialize set with
	 * @return new generic set initialized with specified values
	 */
	public static <V> Set<V> newSet(Collection<? extends V> values) {
		return new HashSet<V>(values);
	}

	/**
	 * Creates new generic set initialized with specified values.
	 *
	 * @param <V>    type of values
	 * @param values values to initialize set with
	 * @return new generic set initialized with specified values
	 */
	public static <V, T extends V> Set<V> newSet(T... values) {
		return new HashSet<V>(Arrays.asList(values));
	}

	/**
	 * Creates new empty thread safe set.
	 *
	 * @param <V> type of values
	 * @return new empty thread safe set
	 */
	public static <V> Set<V> newConcurrentSet() {
		return new CopyOnWriteArraySet<V>();
	}

	/**
	 * Creates new thread safe set initialized with specified values.
	 *
	 * @param <V>    type of values
	 * @param values values to initialize set with
	 * @return new thread safe set initialized with specified values
	 */
	public static <V> Set<V> newConcurrentSet(Collection<? extends V> values) {
		return new CopyOnWriteArraySet<V>(values);
	}

	/**
	 * Creates new thread safe set initialized with specified values.
	 *
	 * @param <V>    type of values
	 * @param values values to initialize set with
	 * @return new thread safe set initialized with specified values
	 */
	public static <V, T extends V> Set<V> newConcurrentSet(T... values) {
		return new CopyOnWriteArraySet<V>(Arrays.asList(values));
	}

	/**
	 * Creates new empty generic list.
	 *
	 * @param <V> type of values
	 * @return new empty list
	 */
	public static <V> List<V> newList() {
		return new ArrayList<V>();
	}

	/**
	 * Creates new generic list initialized with specified values.
	 *
	 * @param <V>    type of values
	 * @param values values to initialize list with
	 * @return new generic list initialized with specified values
	 */
	public static <V> List<V> newList(Collection<? extends V> values) {
		return new ArrayList<V>(values);
	}

	/**
	 * Creates new generic list initialized with specified values.
	 *
	 * @param <V>    type of values
	 * @param values values to initialize list with
	 * @return new generic list initialized with specified values
	 */
	public static <V, T extends V> List<V> newList(T... values) {
		return new ArrayList<V>(Arrays.asList(values));
	}

	/**
	 * Creates new empty thread safe list.
	 *
	 * @param <V> type of values
	 * @return new empty thread safe list
	 */
	public static <V> List<V> newConcurrentList() {
		return new CopyOnWriteArrayList<V>();
	}

	/**
	 * Creates new thread safe list initialized with specified values.
	 *
	 * @param <V>    type of values
	 * @param values values to initialize list with
	 * @return new thread safe list initialized with specified values
	 */
	public static <V> List<V> newConcurrentList(Collection<? extends V> values) {
		return new CopyOnWriteArrayList<V>(values);
	}

	/**
	 * Creates new thread safe list initialized with specified values.
	 *
	 * @param <V>    type of values
	 * @param values values to initialize list with
	 * @return new thread safe list initialized with specified values
	 */
	public static <V, T extends V> List<V> newConcurrentList(T... values) {
		return new CopyOnWriteArrayList<V>(Arrays.asList(values));
	}

	/**
	 * Creates new empty generic map.
	 *
	 * @param <K> type of keys
	 * @param <V> type of values
	 * @return new empty generic map
	 */
	public static <K, V> Map<K, V> newMap() {
		return new HashMap<K, V>();
	}

	/**
	 * Creates new generic map initialized with specified values.
	 *
	 * @param <K>    type of keys
	 * @param <V>    type of values
	 * @param values values to initialize map with
	 * @return new generic map initialized with specified values
	 */
	public static <K, V> Map<K, V> newMap(Map<? extends K, ? extends V> values) {
		return new HashMap<K, V>(values);
	}

	/**
	 * Creates new generic map initialized with keys like even elements and values like odd elements.
	 *
	 * @param <K>    type of keys
	 * @param <V>    type of values
	 * @param values values to initialize map with
	 * @return new generic map initialized with keys like array even elements and values like odd elements
	 */
	@SuppressWarnings({"unchecked"})
	public static <K, V> Map<K, V> newMap(Object... values) {
		final Map<K, V> map = newMap();
		return fillMap(map, values);
	}

	/**
	 * Creates new empty thread safe map.
	 *
	 * @param <K> type of keys
	 * @param <V> type of values
	 * @return new empty thread safe map
	 */
	public static <K, V> Map<K, V> newConcurrentMap() {
		return new ConcurrentHashMap<K, V>();
	}

	/**
	 * Creates new thread safe map initialized with specified values.
	 *
	 * @param <K>    type of keys
	 * @param <V>    type of values
	 * @param values values to initialize map with
	 * @return new thread safe map initialized with specified values
	 */
	public static <K, V> Map<K, V> newConcurrentMap(Map<? extends K, ? extends V> values) {
		return new ConcurrentHashMap<K, V>(values);
	}

	/**
	 * Creates new thread safe map initialized with keys like even elements and values like odd elements.
	 *
	 * @param <K>    type of keys
	 * @param <V>    type of values
	 * @param values values to initialize map with
	 * @return new thread safe map initialized with keys like array even elements and values like odd elements
	 */
	public static <K, V> Map<K, V> newConcurrentMap(Object... values) {
		final Map<K, V> map = newConcurrentMap();
		return fillMap(map, values);
	}

	/**
	 * Fills map with keys like even elements and values like odd elements.
	 *
	 * @param <K>    type of keys
	 * @param <V>    type of values
	 * @param map	map to fill
	 * @param values values to fill map with
	 * @return filled with specified values map
	 */
	@SuppressWarnings({"unchecked"})
	private static <K, V> Map<K, V> fillMap(Map<K, V> map, Object... values) {
		if (values == null || values.length % 2 == 1) {
			throw new IllegalArgumentException(CommonMessages.ordValuesLength());
		}
		for (int i = 0; i < values.length; i += 2) {
			map.put((K) values[i], (V) values[i + 1]);
		}
		return map;
	}
}
