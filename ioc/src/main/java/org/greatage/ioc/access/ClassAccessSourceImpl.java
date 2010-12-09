package org.greatage.ioc.access;

import org.greatage.util.CollectionUtils;

import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class ClassAccessSourceImpl implements ClassAccessSource {
	private final Map<Class, ClassAccess> accesses = CollectionUtils.newConcurrentMap();

	public ClassAccess getAccess(final Class clazz) {
		if (!accesses.containsKey(clazz)) {
			final ClassAccess access = buildClassAccess(clazz);
			accesses.put(clazz, access);
		}
		return accesses.get(clazz);
	}

	private synchronized ClassAccess buildClassAccess(final Class clazz) {
		return new ClassAccessImpl(clazz);
	}
}
