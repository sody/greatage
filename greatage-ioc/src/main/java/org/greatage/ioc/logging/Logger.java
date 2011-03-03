/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.logging;

/**
 * This interface represents enhanced slf4j logger that provides simplified access to all log operations.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Logger {

	/**
	 * Gets logger name.
	 *
	 * @return logger name
	 */
	String getName();

	/**
	 * Log a formatted with specified parameters message at the TRACE level. The last parameter can be attached exception.
	 *
	 * @param format	 format string
	 * @param parameters parameters to format message with
	 */
	void trace(String format, Object... parameters);

	/**
	 * Log a formatted with specified parameters message at the DEBUG level. The last parameter can be attached exception.
	 *
	 * @param format	 format string
	 * @param parameters parameters to format message with
	 */
	void debug(String format, Object... parameters);

	/**
	 * Log a formatted with specified parameters message at the INFO level. The last parameter can be attached exception.
	 *
	 * @param format	 format string
	 * @param parameters parameters to format message with, the last parameter can be attached exception
	 */
	void info(String format, Object... parameters);

	/**
	 * Log a formatted with specified parameters message at the WARN level. The last parameter can be attached exception.
	 *
	 * @param format	 format string
	 * @param parameters parameters to format message with, the last parameter can be attached exception
	 */
	void warn(String format, Object... parameters);

	/**
	 * Log a formatted with specified parameters message at the ERROR level. The last parameter can be attached exception.
	 *
	 * @param format	 format string
	 * @param parameters parameters to format message with, the last parameter can be attached exception
	 */
	void error(String format, Object... parameters);
}
