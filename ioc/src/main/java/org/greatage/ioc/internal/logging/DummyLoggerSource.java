/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.internal.logging;

import org.greatage.ioc.services.Logger;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class DummyLoggerSource extends AbstractLoggerSource {

	public Logger getLogger(final String name) {
		return new DummyLogger(name);
	}

}
