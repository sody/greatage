/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
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
