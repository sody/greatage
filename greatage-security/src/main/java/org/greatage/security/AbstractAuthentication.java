package org.greatage.security;

import org.greatage.util.DescriptionBuilder;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractAuthentication implements Authentication {

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || !(getClass().isInstance(o))) {
			return false;
		}
		final Authentication entity = (Authentication) o;
		return !(getName() != null ? !getName().equals(entity.getName()) : entity.getName() != null);
	}

	@Override
	public int hashCode() {
		return getName() == null ? super.hashCode() : getName().hashCode();
	}

	@Override
	public String toString() {
		return new DescriptionBuilder(getClass()).append("name", getName()).toString();
	}
}
