package org.greatage.ioc.coerce;

/**
 * This class represents {@link Coercion} implementation that converts values from boolean to string.
 *
 * @author Ivan Khalopik
 * @since 1.1
 */
public class BooleanToStringCoercion extends AbstractCoercion<Boolean, String> {

	/**
	 * Creates new coercion instance that converts values from boolean to string.
	 */
	public BooleanToStringCoercion() {
		super(Boolean.class, String.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public String coerce(final Boolean source) {
		return source.toString();
	}
}
