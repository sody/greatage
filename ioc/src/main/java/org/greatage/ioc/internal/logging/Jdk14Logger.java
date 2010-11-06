/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.internal.logging;

import java.util.logging.Level;

/**
 * This class represents logger implementation for JDK 1.4 logging API.
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
	public Jdk14Logger(java.util.logging.Logger logger) {
		delegate = logger;
	}

	public void trace(Throwable exception, String format, Object... parameters) {
		log(Level.FINEST, exception, format, parameters);
	}

	public void debug(Throwable exception, String format, Object... parameters) {
		log(Level.FINE, exception, format, parameters);
	}

	public void info(Throwable exception, String format, Object... parameters) {
		log(Level.INFO, exception, format, parameters);
	}

	public void warn(Throwable exception, String format, Object... parameters) {
		log(Level.WARNING, exception, format, parameters);
	}

	public void error(Throwable exception, String format, Object... parameters) {
		log(Level.SEVERE, exception, format, parameters);
	}

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
	private void log(Level level, Throwable exception, String format, Object... parameters) {
		delegate.log(level, String.format(format, parameters), exception);
	}
}
