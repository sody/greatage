/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.logging;

/**
 * This interface represents logger that provides simplified access to all log operations.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Logger {

	/**
	 * Log a formatted with specified parameters message at the TRACE level.
	 *
	 * @param format	 format string
	 * @param parameters parameters to format message with
	 */
	void trace(String format, Object... parameters);

	/**
	 * Log a formatted with specified parameters message and attached exception at the TRACE level.
	 *
	 * @param exception  attached exception
	 * @param format	 format string
	 * @param parameters parameters to format message with
	 */
	void trace(Throwable exception, String format, Object... parameters);

	/**
	 * Log a formatted with specified parameters message at the DEBUG level.
	 *
	 * @param format	 format string
	 * @param parameters parameters to format message with
	 */
	void debug(String format, Object... parameters);

	/**
	 * Log a formatted with specified parameters message and attached exception at the DEBUG level.
	 *
	 * @param exception  attached exception
	 * @param format	 format string
	 * @param parameters parameters to format message with
	 */
	void debug(Throwable exception, String format, Object... parameters);

	/**
	 * Log a formatted with specified parameters message at the INFO level.
	 *
	 * @param format	 format string
	 * @param parameters parameters to format message with
	 */
	void info(String format, Object... parameters);

	/**
	 * Log a formatted with specified parameters message and attached exception at the INFO level.
	 *
	 * @param exception  attached exception
	 * @param format	 format string
	 * @param parameters parameters to format message with
	 */
	void info(Throwable exception, String format, Object... parameters);

	/**
	 * Log a formatted with specified parameters message at the WARN level.
	 *
	 * @param format	 format string
	 * @param parameters parameters to format message with
	 */
	void warn(String format, Object... parameters);

	/**
	 * Log a formatted with specified parameters message and attached exception at the WARN level.
	 *
	 * @param exception  attached exception
	 * @param format	 format string
	 * @param parameters parameters to format message with
	 */
	void warn(Throwable exception, String format, Object... parameters);

	/**
	 * Log a formatted with specified parameters message at the ERROR level.
	 *
	 * @param format	 format string
	 * @param parameters parameters to format message with
	 */
	void error(String format, Object... parameters);

	/**
	 * Log a formatted with specified parameters message and attached exception at the ERROR level.
	 *
	 * @param exception  attached exception
	 * @param format	 format string
	 * @param parameters parameters to format message with
	 */
	void error(Throwable exception, String format, Object... parameters);
}
