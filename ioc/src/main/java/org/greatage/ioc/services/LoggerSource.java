/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.services;

/**
 * This interface represents utility producing Loggers for various logging APIs by their class or name.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface LoggerSource {

	/**
	 * Creates or retrieves logger based on specified class.
	 *
	 * @param clazz class
	 * @return logger instance
	 */
	Logger getLogger(Class clazz);

	/**
	 * Creates or retrieves logger based on specified name.
	 *
	 * @param name name
	 * @return logger instance
	 */
	Logger getLogger(String name);

}
