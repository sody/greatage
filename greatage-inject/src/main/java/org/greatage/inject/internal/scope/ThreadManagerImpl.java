package org.greatage.inject.internal.scope;

import org.greatage.inject.Marker;
import org.greatage.inject.services.ThreadManager;
import org.greatage.util.CollectionUtils;

import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ThreadManagerImpl implements ThreadManager {
	private final ThreadLocal<Map<Marker, Object>> objects = new ThreadLocal<Map<Marker, Object>>() {
		@Override
		protected Map<Marker, Object> initialValue() {
			return CollectionUtils.newConcurrentMap();
		}
	};

	public <T> T get(final Marker<T> marker) {
		return marker.getServiceClass().cast(objects.get().get(marker));
	}

	public <T> void put(final Marker<T> marker, final T instance) {
		objects.get().put(marker, instance);
	}

	public void clear() {
		objects.remove();
	}
}
