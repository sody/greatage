package org.greatage.ioc.coerce;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class BooleanToStringCoercion extends AbstractCoercion<Boolean, String> {

	public BooleanToStringCoercion() {
		super(Boolean.class, String.class);
	}

	public String coerce(final Boolean source) {
		return source.toString();
	}
}
