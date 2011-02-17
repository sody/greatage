package org.greatage.ioc.coerce;

/**
 * This class represents {@link Coercion} implementation that converts values from string to double.
 *
 * @author Ivan Khalopik
 * @since 1.1
 */
public class StringToDoubleCoercion extends AbstractCoercion<String, Double> {

	/**
	 * Creates new coercion instance that converts values from string to double.
	 */
	public StringToDoubleCoercion() {
		super(String.class, Double.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public Double coerce(final String source) {
		try {
			return Double.parseDouble(source);
		} catch (NumberFormatException e) {
			throw new CoerceException(source, getTargetClass(), e);
		}
	}
}
