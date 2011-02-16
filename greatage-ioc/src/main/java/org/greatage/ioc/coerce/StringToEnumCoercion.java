package org.greatage.ioc.coerce;

import org.greatage.util.CollectionUtils;

import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class StringToEnumCoercion<E extends Enum<E>> extends AbstractCoercion<String, E> {
	private final Map<String, E> stringToEnum = CollectionUtils.newMap();

	public StringToEnumCoercion(final Class<E> targetClass) {
		super(String.class, targetClass);
		for (E e : getTargetClass().getEnumConstants()) {
			stringToEnum.put(e.name().toLowerCase(), e);
		}
	}

	public E coerce(final String source) {
		final String key = source.toLowerCase();
		if (!stringToEnum.containsKey(key)) {
			throw new IllegalArgumentException(
					String.format("Can not coerce '%s' string to '%s'", source, getTargetClass()));
		}
		return stringToEnum.get(key);
	}
}
