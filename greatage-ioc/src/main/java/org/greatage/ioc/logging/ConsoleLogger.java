/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
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
