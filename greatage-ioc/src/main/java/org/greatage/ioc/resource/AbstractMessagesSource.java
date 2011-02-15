/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.resource;

import org.greatage.util.DescriptionBuilder;

import java.util.Locale;

/**
 * This class represents abstract implementation of messages source that delegates messages creation by class to
 * creation by class name.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractMessagesSource implements MessagesSource {

	/**
	 * {@inheritDoc} Delegates invocation to {@link #getMessages(String, java.util.Locale)} method.
	 */
	public Messages getMessages(final Class clazz, final Locale locale) {
		final String resourceName = clazz.getName().replace('.', '/');
		return getMessages(resourceName, locale);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return new DescriptionBuilder(getClass()).toString();
	}
}
