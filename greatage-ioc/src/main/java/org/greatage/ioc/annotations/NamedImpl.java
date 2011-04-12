package org.greatage.ioc.annotations;

import java.io.Serializable;
import java.lang.annotation.Annotation;

/**
 * @author Ivan Khalopik
 * @since 8.0
 */
public class NamedImpl implements Named, Serializable {
	private final String value;

	public NamedImpl(final String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

	public Class<? extends Annotation> annotationType() {
		return Named.class;
	}

	@Override
	public int hashCode() {
		return (127 * "value".hashCode()) ^ value.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof Named)) {
			return false;
		}

		Named other = (Named) obj;
		return value.equals(other.value());
	}
}
