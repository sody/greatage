package org.greatage.security.auth;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface PasswordEncoder {

	String encode(String password);

}
