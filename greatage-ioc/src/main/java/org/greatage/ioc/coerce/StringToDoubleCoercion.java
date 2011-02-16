package org.greatage.ioc.coerce;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class StringToDoubleCoercion extends AbstractCoercion<String, Double> {
	public StringToDoubleCoercion() {
		super(String.class, Double.class);
	}

	public Double coerce(final String source) {
		return Double.parseDouble(source);
	}
}
