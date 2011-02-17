/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.logging;

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
	public Slf4jLogger(final org.slf4j.Logger logger) {
		delegate = logger;
	}

	/**
	 * {@inheritDoc}
	 */
	public void trace(final Throwable exception, final String format, final Object... parameters) {
		final String message = String.format(format, parameters);
		if (exception != null) {
			delegate.trace(message, exception);
		} else {
			delegate.trace(message);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void debug(final Throwable exception, final String format, final Object... parameters) {
		final String message = String.format(format, parameters);
		if (exception != null) {
			delegate.debug(message, exception);
		} else {
			delegate.debug(message);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void info(final Throwable exception, final String format, final Object... parameters) {
		final String message = String.format(format, parameters);
		if (exception != null) {
			delegate.info(message, exception);
		} else {
			delegate.info(message);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void warn(final Throwable exception, final String format, final Object... parameters) {
		final String message = String.format(format, parameters);
		if (exception != null) {
			delegate.warn(message, exception);
		} else {
			delegate.warn(message);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void error(final Throwable exception, final String format, final Object... parameters) {
		final String message = String.format(format, parameters);
		if (exception != null) {
			delegate.error(message, exception);
		} else {
			delegate.error(message);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getName() {
		return delegate.getName();
	}
}