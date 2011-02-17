package org.greatage.ioc.coerce;

import org.greatage.util.CollectionUtils;

import java.util.Map;

/**
 * This class represents {@link Coercion} implementation that converts enum constants to string.
 *
 * @param <E> enum type
 * @author Ivan Khalopik
 * @since 1.1
 */
public class EnumToStringCoercion<E extends Enum<E>> extends AbstractCoercion<E, String> {
	private final Map<E, String> enumToString = CollectionUtils.newMap();

	/**
	 * Creates new coercion instance that converts enum constants to string.
	 *
	 * @param sourceClass enum class
	 */
	public EnumToStringCoercion(final Class<E> sourceClass) {
		super(sourceClass, String.class);
		for (E e : sourceClass.getEnumConstants()) {
			enumToString.put(e, e.name().toLowerCase());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public String coerce(final E source) {
		return enumToString.get(source);
	}
}
