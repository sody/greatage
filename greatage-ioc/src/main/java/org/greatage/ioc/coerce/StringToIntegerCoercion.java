package org.greatage.ioc.coerce;

/**
 * This class represents {@link Coercion} implementation that converts values from string to integer.
 *
 * @author Ivan Khalopik
 * @since 1.1
 */
public class StringToIntegerCoercion extends AbstractCoercion<String, Integer> {

	/**
	 * Creates new coercion instance that converts values from string to integer.
	 */
	public StringToIntegerCoercion() {
		super(String.class, Integer.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public Integer coerce(final String source) {
		try {
			return Integer.parseInt(source);
		} catch (NumberFormatException e) {
			throw new CoerceException(source, getTargetClass(), e);
		}
	}
}
