package org.greatage.ioc.coerce;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class StringToIntegerCoercion extends AbstractCoercion<String, Integer> {
	public StringToIntegerCoercion() {
		super(String.class, Integer.class);
	}

	public Integer coerce(final String source) {
		return Integer.parseInt(source);
	}
}
