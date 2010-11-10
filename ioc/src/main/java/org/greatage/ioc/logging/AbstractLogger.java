/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.logging;

import org.greatage.util.DescriptionBuilder;

/**
 * This class represent abstract logger implementation that delegates log methods without exception parameter to those
 * with this parameter.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractLogger implements Logger {

	public void trace(String format, Object... parameters) {
		trace(null, format, parameters);
	}

	public void debug(String format, Object... parameters) {
		debug(null, format, parameters);
	}

	public void info(String format, Object... parameters) {
		info(null, format, parameters);
	}

	public void warn(String format, Object... parameters) {
		warn(null, format, parameters);
	}

	public void error(String format, Object... parameters) {
		error(null, format, parameters);
	}

	protected abstract String getName();

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append(getName());
		return builder.toString();
	}
}
