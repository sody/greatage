package org.greatage.security.auth;

import org.greatage.util.CollectionUtils;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class DefaultAuthentication implements Authentication {
	private final String name;
	private final List<String> authorities;
	private final String encodedPassword;

	public DefaultAuthentication(final String name, final String encodedPassword, List<String> authorities) {
		this.name = name;
		this.authorities = authorities;
		this.encodedPassword = encodedPassword;
	}

	public DefaultAuthentication(final String name, final String encodedPassword, String... authorities) {
		this(name, encodedPassword, CollectionUtils.newList(authorities));
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return encodedPassword;
	}

	public List<String> getAuthorities() {
		return authorities;
	}
}
