/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.internal.logging;

/**
 * This class represents logger implementation through Slf4j logging API.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Slf4jLogger extends AbstractLogger {
	private final org.slf4j.Logger delegate;

	/**
	 * Creates new logger instance through Slf4j logging API.
	 *
	 * @param logger Slf4j logger
	 */
	public Slf4jLogger(org.slf4j.Logger logger) {
		delegate = logger;
	}

	public void trace(Throwable exception, String format, Object... parameters) {
		final String message = String.format(format, parameters);
		if (exception != null) {
			delegate.trace(message, exception);
		} else {
			delegate.trace(message);
		}
	}

	public void debug(Throwable exception, String format, Object... parameters) {
		final String message = String.format(format, parameters);
		if (exception != null) {
			delegate.debug(message, exception);
		} else {
			delegate.debug(message);
		}
	}

	public void info(Throwable exception, String format, Object... parameters) {
		final String message = String.format(format, parameters);
		if (exception != null) {
			delegate.info(message, exception);
		} else {
			delegate.info(message);
		}
	}

	public void warn(Throwable exception, String format, Object... parameters) {
		final String message = String.format(format, parameters);
		if (exception != null) {
			delegate.warn(message, exception);
		} else {
			delegate.warn(message);
		}
	}

	public void error(Throwable exception, String format, Object... parameters) {
		final String message = String.format(format, parameters);
		if (exception != null) {
			delegate.error(message, exception);
		} else {
			delegate.error(message);
		}
	}

	@Override
	protected String getName() {
		return delegate.getName();
	}
}
