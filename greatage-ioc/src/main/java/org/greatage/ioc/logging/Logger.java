/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
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
 * This interface represents enhanced slf4j logger that provides simplified access to all log operations.
 *
 * @author Ivan Khalopik
 * @since 1.1
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
