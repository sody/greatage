/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
