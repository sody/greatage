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

import org.apache.log4j.Level;

/**
 * This class represents logger implementation for Log4j logging API.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Log4jLogger extends AbstractLogger {
	private static final String FQCN = Log4jLogger.class.getName();
	private final org.apache.log4j.Logger delegate;

	/**
	 * Creates new logger instance for Log4j logging API.
	 *
	 * @param logger Log4j logger
	 */
	public Log4jLogger(final org.apache.log4j.Logger logger) {
		delegate = logger;
	}

	/**
	 * {@inheritDoc}
	 */
	public void trace(final Throwable exception, final String format, final Object... parameters) {
		log(Level.TRACE, exception, format, parameters);
	}

	/**
	 * {@inheritDoc}
	 */
	public void debug(final Throwable exception, final String format, final Object... parameters) {
		log(Level.DEBUG, exception, format, parameters);
	}

	/**
	 * {@inheritDoc}
	 */
	public void info(final Throwable exception, final String format, final Object... parameters) {
		log(Level.INFO, exception, format, parameters);
	}

	/**
	 * {@inheritDoc}
	 */
	public void warn(final Throwable exception, final String format, final Object... parameters) {
		log(Level.WARN, exception, format, parameters);
	}

	/**
	 * {@inheritDoc}
	 */
	public void error(final Throwable exception, final String format, final Object... parameters) {
		log(Level.ERROR, exception, format, parameters);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getName() {
		return delegate.getName();
	}

	/**
	 * Log a formatted with specified parameters message and attached exception at the specified level.
	 *
	 * @param level	  log level
	 * @param exception  attached exception
	 * @param format	 message format
	 * @param parameters message parameters
	 */
	private void log(final Level level, final Throwable exception, final String format, final Object... parameters) {
		delegate.log(FQCN, level, String.format(format, parameters), exception);
	}
}
