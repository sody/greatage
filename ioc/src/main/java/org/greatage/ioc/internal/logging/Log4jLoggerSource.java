/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.internal.logging;

import org.apache.log4j.LogManager;
import org.greatage.ioc.services.Logger;

/**
 * This class represents logger source implementation for Log4j logging API.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Log4jLoggerSource extends AbstractLoggerSource {

	public Logger getLogger(String name) {
		return new Log4jLogger(LogManager.getLogger(name));
	}

}
