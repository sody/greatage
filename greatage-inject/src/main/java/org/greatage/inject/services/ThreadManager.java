package org.greatage.inject.services;

import org.greatage.inject.Marker;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ThreadManager {

	<T> T get(Marker<T> marker);

	<T> void put(Marker<T> marker, T instance);

	void clear();
}
