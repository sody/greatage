package org.greatage.resource;

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

	public Messages getMessages(final Class clazz, final Locale locale) {
		return getMessages(clazz.getName(), locale);
	}

	@Override
	public String toString() {
		return new DescriptionBuilder(getClass()).toString();
	}
}
