package org.greatage.ioc.services;

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
	public void trace(String format, Object... parameters);

	/**
	 * Log a formatted with specified parameters message and attached exception at the TRACE level.
	 *
	 * @param exception  attached exception
	 * @param format	 format string
	 * @param parameters parameters to format message with
	 */
	public void trace(Throwable exception, String format, Object... parameters);

	/**
	 * Log a formatted with specified parameters message at the DEBUG level.
	 *
	 * @param format	 format string
	 * @param parameters parameters to format message with
	 */
	public void debug(String format, Object... parameters);

	/**
	 * Log a formatted with specified parameters message and attached exception at the DEBUG level.
	 *
	 * @param exception  attached exception
	 * @param format	 format string
	 * @param parameters parameters to format message with
	 */
	public void debug(Throwable exception, String format, Object... parameters);

	/**
	 * Log a formatted with specified parameters message at the INFO level.
	 *
	 * @param format	 format string
	 * @param parameters parameters to format message with
	 */
	public void info(String format, Object... parameters);

	/**
	 * Log a formatted with specified parameters message and attached exception at the INFO level.
	 *
	 * @param exception  attached exception
	 * @param format	 format string
	 * @param parameters parameters to format message with
	 */
	public void info(Throwable exception, String format, Object... parameters);

	/**
	 * Log a formatted with specified parameters message at the WARN level.
	 *
	 * @param format	 format string
	 * @param parameters parameters to format message with
	 */
	public void warn(String format, Object... parameters);

	/**
	 * Log a formatted with specified parameters message and attached exception at the WARN level.
	 *
	 * @param exception  attached exception
	 * @param format	 format string
	 * @param parameters parameters to format message with
	 */
	public void warn(Throwable exception, String format, Object... parameters);

	/**
	 * Log a formatted with specified parameters message at the ERROR level.
	 *
	 * @param format	 format string
	 * @param parameters parameters to format message with
	 */
	public void error(String format, Object... parameters);

	/**
	 * Log a formatted with specified parameters message and attached exception at the ERROR level.
	 *
	 * @param exception  attached exception
	 * @param format	 format string
	 * @param parameters parameters to format message with
	 */
	public void error(Throwable exception, String format, Object... parameters);

}
