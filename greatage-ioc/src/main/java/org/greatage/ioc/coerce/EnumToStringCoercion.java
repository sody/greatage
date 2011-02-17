package org.greatage.ioc.coerce;

/**
 * This class represents {@link Coercion} implementation that converts enum constants to string.
 *
 * @author Ivan Khalopik
 * @since 1.1
 */
public class EnumToStringCoercion extends AbstractCoercion<Enum, String> {

	/**
	 * Creates new coercion instance that converts enum constants to string.
	 */
	public EnumToStringCoercion() {
		super(Enum.class, String.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public String coerce(final Enum source) {
		return source.name().toLowerCase();
	}
}
