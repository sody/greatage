package org.greatage.ioc.coerce;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class NumberToStringCoercion extends AbstractCoercion<Number, String> {

	public NumberToStringCoercion() {
		super(Number.class, String.class);
	}

	public String coerce(final Number source) {
		return source.toString();
	}
}
