/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.resource;

import java.util.Locale;
import java.util.Map;

/**
 * This class represents default implementation of {@link Messages} that uses map for storing localized string.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MessagesImpl extends AbstractMessages {
	private final Map<String, String> messages;

	/**
	 * Creates new instance of messages for specified locale and initialized with specified messages.
	 *
	 * @param locale   messages locale
	 * @param messages values to initialize messages with
	 */
	public MessagesImpl(final Locale locale, final Map<String, String> messages) {
		super(locale);
		this.messages = messages;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean contains(final String key) {
		return messages.containsKey(key);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getMessage(final String key) {
		return messages.get(key);
	}
}