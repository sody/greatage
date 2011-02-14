/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.logging;

import java.util.logging.LogManager;

/**
 * This class represents logger source implementation for JDK 1.4 logging API.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Jdk14LoggerSource extends AbstractLoggerSource {

	/**
	 * {@inheritDoc} Creates {@link Jdk14Logger} instance.
	 */
	public Logger getLogger(final String name) {
		return new Jdk14Logger(LogManager.getLogManager().getLogger(name));
	}
}
