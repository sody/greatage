package org.greatage.ioc.coerce;

import org.greatage.util.CollectionUtils;

import java.util.Map;

/**
 * This class represents {@link Coercion} implementation that converts values from string to enum constant.
 *
 * @param <E> enum type
 * @author Ivan Khalopik
 * @since 1.1
 */
public class StringToEnumCoercion<E extends Enum<E>> extends AbstractCoercion<String, E> {
	private final Map<String, E> stringToEnum = CollectionUtils.newMap();

	/**
	 * Creates new coercion instance that converts values from string to enum constant.
	 *
	 * @param targetClass enum class
	 */
	public StringToEnumCoercion(final Class<E> targetClass) {
		super(String.class, targetClass);
		for (E e : getTargetClass().getEnumConstants()) {
			stringToEnum.put(e.name().toLowerCase(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public E coerce(final String source) {
		final String key = source.toLowerCase();
		if (!stringToEnum.containsKey(key)) {
			throw new CoerceException(source, getTargetClass());
		}
		return stringToEnum.get(key);
	}
}
