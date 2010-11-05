package org.greatage.resource;

import java.util.Locale;
import java.util.Map;

/**
 * This class represents default implementation of {@link Messages} that uses map for storing localized
 * string.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MapMessages extends AbstractMessages {
	private final Map<String, String> messages;

	/**
	 * Creates new instance of messages for specified locale and initialized with specified messages.
	 *
	 * @param locale   messages locale
	 * @param messages values to initialize messages with
	 */
	public MapMessages(Locale locale, Map<String, String> messages) {
		super(locale);
		this.messages = messages;
	}

	@Override
	public boolean contains(String key) {
		return messages.containsKey(key);
	}

	@Override
	protected String getMessage(String key) {
		return messages.get(key);
	}
}
