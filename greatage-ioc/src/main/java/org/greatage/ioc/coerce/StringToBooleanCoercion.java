package org.greatage.ioc.coerce;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class StringToBooleanCoercion extends AbstractCoercion<String, Boolean> {
	public StringToBooleanCoercion() {
		super(String.class, Boolean.class);
	}

	public Boolean coerce(final String source) {
		return Boolean.parseBoolean(source);
	}
}
