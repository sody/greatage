/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.internal.logging;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class DummyLogger extends AbstractLogger {
	private final String name;

	public DummyLogger(final String name) {
		this.name = name;
	}

	@Override
	protected String getName() {
		return name;
	}

	public void trace(final Throwable exception, final String format, final Object... parameters) {
		log("TRACE", exception, format, parameters);
	}

	public void debug(final Throwable exception, final String format, final Object... parameters) {
		log("DEBUG", exception, format, parameters);
	}

	public void info(final Throwable exception, final String format, final Object... parameters) {
		log("INFO", exception, format, parameters);
	}

	public void warn(final Throwable exception, final String format, final Object... parameters) {
		log("WARN", exception, format, parameters);
	}

	public void error(final Throwable exception, final String format, final Object... parameters) {
		log("ERROR", exception, format, parameters);
	}

	private void log(final String level, final Throwable exception, final String format, final Object... parameters) {
		System.out.printf(level + ": " + format, parameters).println();
		if (exception != null) {
			exception.printStackTrace(System.err);
		}
	}
}
