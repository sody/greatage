package org.greatage.ioc.coerce;

/**
 * This class represents {@link Coercion} implementation that converts values from number to string.
 *
 * @author Ivan Khalopik
 * @since 1.1
 */
public class NumberToStringCoercion extends AbstractCoercion<Number, String> {

	/**
	 * Creates new coercion instance that converts values from number to string.
	 */
	public NumberToStringCoercion() {
		super(Number.class, String.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public String coerce(final Number source) {
		return source.toString();
	}
}
