/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.security;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface SecurityContext<T extends Authentication> {

	T getCurrentUser();

	T getUser(String name);

	List<T> getLoggedUsers();

	void initCurrentUser(T user);

	void clearCurrentUser();

	void removeCurrentUser();

}
