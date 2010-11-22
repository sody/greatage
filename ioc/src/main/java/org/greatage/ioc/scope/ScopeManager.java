/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.scope;

/**
 * This interface represents utility service that obtains scope instances by their name.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ScopeManager {

	/**
	 * Obtains scope instance by its name.
	 *
	 * @param scope scope name
	 * @return scope instance correspondent to specified scope name or null if doesn't exist
	 */
	Scope getScope(String scope);

}
