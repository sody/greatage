package org.greatage.ioc.access;

import org.greatage.util.CollectionUtils;

import java.util.Map;

/**
 * This class represents default {@link ClassAccessSource} implementation that gets class access instances by specified
 * class and builds missing.
 *
 * @author Ivan Khalopik
 * @since 1.1
 */
public class ClassAccessSourceImpl implements ClassAccessSource {
	private final Map<Class, ClassAccess> accesses = CollectionUtils.newConcurrentMap();

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public <T> ClassAccess<T> getAccess(final Class<T> clazz) {
		assert clazz != null;

		if (!accesses.containsKey(clazz)) {
			final ClassAccess<T> access = buildClassAccess(clazz);
			accesses.put(clazz, access);
		}
		return accesses.get(clazz);
	}

	/**
	 * Creates new class access instance for specified bean class.
	 *
	 * @param clazz bean class
	 * @param <T>   bean type
	 * @return new class access instance
	 * @throws PropertyAccessException when error occurs while getting class access
	 */
	private <T> ClassAccess<T> buildClassAccess(final Class<T> clazz) {
		return new ClassAccessImpl<T>(clazz);
	}
}
