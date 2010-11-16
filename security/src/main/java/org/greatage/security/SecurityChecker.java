/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.security;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface SecurityChecker {

	void checkPermission(Object securedObject, String permission);

	void checkAuthority(String authority);

}
