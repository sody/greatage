package org.greatage.security.context;

import org.greatage.security.auth.Authentication;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface UserContext<U extends Authentication> {

	U getUser();

	void setUser(U user);

}
