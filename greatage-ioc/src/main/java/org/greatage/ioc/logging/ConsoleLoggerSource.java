/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.logging;

/**
 * This class represents {@link LoggerSource} service implementation that creates {@link ConsoleLogger} instances.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ConsoleLoggerSource extends AbstractLoggerSource {

	/**
	 * {@inheritDoc} Creates {@link ConsoleLogger} instance.
	 */
	public Logger getLogger(final String name) {
		return new ConsoleLogger(name);
	}
}
