/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.scope;

/**
 * This class represents all base pre-defined scope identifiers.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class ScopeConstants {

	/**
	 * Identifier for global scope. It is used for services that have the same state for whole application.
	 */
	public static final String GLOBAL = "global";

	/**
	 * Identifier for prototype scope. It is used for services that have different state for all points where it is
	 * accessed.
	 */
	public static final String PROTOTYPE = "prototype";

	/**
	 * Identifier for thread scope. It is used for services that have the same state inside one application thread.
	 */
	public static final String THREAD = "thread";
}
