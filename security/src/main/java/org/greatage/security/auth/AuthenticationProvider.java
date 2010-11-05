package org.greatage.security.auth;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface AuthenticationProvider {

	boolean supports(AuthenticationToken token);

	Authentication authenticate(AuthenticationToken token) throws AuthenticationException;

}
