package org.greatage.security;

import org.greatage.util.DescriptionBuilder;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class User implements Authentication {
	private final String name;
	private final List<String> authorities;

	public User(final String name, final List<String> authorities) {
		this.name = name;
		this.authorities = authorities;
	}

	public String getName() {
		return name;
	}

	public List<String> getAuthorities() {
		return authorities;
	}

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
		return new DescriptionBuilder(getClass())
				.append("name", getName())
				.append("authorities", authorities)
				.toString();
	}

}
