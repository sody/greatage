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

	public Logger getLogger(Class clazz) {
		return getLogger(clazz.getName());
	}

	@Override
	public String toString() {
		return new DescriptionBuilder(getClass()).toString();
	}
}
