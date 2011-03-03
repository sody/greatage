/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.logging;

import org.greatage.util.DescriptionBuilder;
import org.slf4j.LoggerFactory;

/**
 * This class represents logger source implementation through Slf4j logging API.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Slf4jLoggerSource implements LoggerSource {

	/**
	 * {@inheritDoc} Creates {@link Slf4jLogger} instance.
	 */
	public Logger getLogger(final String name) {
		return new Slf4jLogger(LoggerFactory.getLogger(name));
	}

	/**
	 * {@inheritDoc} Creates {@link Slf4jLogger} instance.
	 */
	public Logger getLogger(final Class clazz) {
		return new Slf4jLogger(LoggerFactory.getLogger(clazz));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return new DescriptionBuilder(getClass()).toString();
	}
}
