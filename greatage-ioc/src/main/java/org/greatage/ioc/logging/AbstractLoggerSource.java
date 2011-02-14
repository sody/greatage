/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.logging;

import org.greatage.util.DescriptionBuilder;

/**
 * This class represents abstract logger source implementation that delegates creation of logger by class to creation by
 * class name.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractLoggerSource implements LoggerSource {

	/**
	 * {@inheritDoc} Delegates invocation to {@link #getLogger(String)} method.
	 */
	public Logger getLogger(final Class clazz) {
		return getLogger(clazz.getName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return new DescriptionBuilder(getClass()).toString();
	}
}
