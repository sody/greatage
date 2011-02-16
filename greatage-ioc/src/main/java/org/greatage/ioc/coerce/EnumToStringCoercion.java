package org.greatage.ioc.coerce;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class EnumToStringCoercion<E extends Enum<E>> extends AbstractCoercion<E, String> {

	public EnumToStringCoercion(final Class<E> sourceClass) {
		super(sourceClass, String.class);
	}

	public String coerce(final E source) {
		return source.name().toLowerCase();
	}
}
