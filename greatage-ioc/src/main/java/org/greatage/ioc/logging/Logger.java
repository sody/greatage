/*
 * Copyright 2011 Ivan Khalopik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
