/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
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
	public Log4jLogger(org.apache.log4j.Logger logger) {
		delegate = logger;
	}

	public void trace(Throwable exception, String format, Object... parameters) {
		log(Level.TRACE, exception, format, parameters);
	}

	public void debug(Throwable exception, String format, Object... parameters) {
		log(Level.DEBUG, exception, format, parameters);
	}

	public void info(Throwable exception, String format, Object... parameters) {
		log(Level.INFO, exception, format, parameters);
	}

	public void warn(Throwable exception, String format, Object... parameters) {
		log(Level.WARN, exception, format, parameters);
	}

	public void error(Throwable exception, String format, Object... parameters) {
		log(Level.ERROR, exception, format, parameters);
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
		delegate.log(FQCN, level, String.format(format, parameters), exception);
	}
}
