package org.greatage.ioc.coerce;

/**
 * This class represents {@link Coercion} implementation that converts values from string to boolean.
 *
 * @author Ivan Khalopik
 * @since 1.1
 */
public class StringToBooleanCoercion extends AbstractCoercion<String, Boolean> {
	private static final String VALUE_TRUE = "true";
	private static final String VALUE_FALSE = "false";

	/**
	 * Creates new coercion instance that converts values from string to boolean.
	 */
	public StringToBooleanCoercion() {
		super(String.class, Boolean.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public Boolean coerce(final String source) {
		if (VALUE_TRUE.equalsIgnoreCase(source)) {
			return Boolean.TRUE;
		} else if (VALUE_FALSE.equalsIgnoreCase(source)) {
			return Boolean.FALSE;
		}
		throw new CoerceException(source, getTargetClass());
	}
}
