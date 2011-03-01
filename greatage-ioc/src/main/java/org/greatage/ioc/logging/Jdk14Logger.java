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

import java.util.logging.Level;

/**
 * This class represents logger implementation for JDK 1.4 logging API. It uses next level mapping: trace -> finest,
 * debug -> fine, info -> info, warn -> warning, error -> severe.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Jdk14Logger extends AbstractLogger {
	private final java.util.logging.Logger delegate;

	/**
	 * Creates new logger instance for JDK 1.4 logging API.
	 *
	 * @param logger JDK 1.4 logger
	 */
	public Jdk14Logger(final java.util.logging.Logger logger) {
		delegate = logger;
	}

	/**
	 * {@inheritDoc}
	 */
	public void trace(final Throwable exception, final String format, final Object... parameters) {
		log(Level.FINEST, exception, format, parameters);
	}

	/**
	 * {@inheritDoc}
	 */
	public void debug(final Throwable exception, final String format, final Object... parameters) {
		log(Level.FINE, exception, format, parameters);
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
		log(Level.WARNING, exception, format, parameters);
	}

	/**
	 * {@inheritDoc}
	 */
	public void error(final Throwable exception, final String format, final Object... parameters) {
		log(Level.SEVERE, exception, format, parameters);
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
		delegate.log(level, String.format(format, parameters), exception);
	}
}
