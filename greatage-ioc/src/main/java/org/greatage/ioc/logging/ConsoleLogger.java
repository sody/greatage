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
 * This class represents dummy {@link Logger} implementation that logs all messages in console {@link System#out}.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ConsoleLogger extends AbstractLogger {
	private final String name;

	/**
	 * Creates new instance of console logger with defined name that logs all messages in console {@link System#out}.
	 *
	 * @param name log name
	 */
	public ConsoleLogger(final String name) {
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	public void trace(final Throwable exception, final String format, final Object... parameters) {
		log("TRACE", exception, format, parameters);
	}

	/**
	 * {@inheritDoc}
	 */
	public void debug(final Throwable exception, final String format, final Object... parameters) {
		log("DEBUG", exception, format, parameters);
	}

	/**
	 * {@inheritDoc}
	 */
	public void info(final Throwable exception, final String format, final Object... parameters) {
		log("INFO", exception, format, parameters);
	}

	/**
	 * {@inheritDoc}
	 */
	public void warn(final Throwable exception, final String format, final Object... parameters) {
		log("WARN", exception, format, parameters);
	}

	/**
	 * {@inheritDoc}
	 */
	public void error(final Throwable exception, final String format, final Object... parameters) {
		log("ERROR", exception, format, parameters);
	}

	/**
	 * Logs message to {@link System#out} as <tt>level: formatted string</tt> and if exception is not null prints it in
	 * {@link System#err}.
	 *
	 * @param level	  log level
	 * @param exception  attached exception
	 * @param format	 message format
	 * @param parameters message parameters
	 */
	private void log(final String level, final Throwable exception, final String format, final Object... parameters) {
		System.out.printf(level + ": " + format, parameters).println();
		if (exception != null) {
			exception.printStackTrace(System.err);
		}
	}
}
