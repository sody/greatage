/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.resource;

import java.util.Locale;

/**
 * This interface represents utility producing messages by class or name.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface MessagesSource {

	/**
	 * Creates or retrieves localized messages in specified locale based on specified class.
	 *
	 * @param clazz  messages class
	 * @param locale messages locale
	 * @return localized messages
	 */
	Messages getMessages(Class clazz, Locale locale);

	/**
	 * Creates or retrieves localized messages in specified locale based on specified name.
	 *
	 * @param name   messages name
	 * @param locale messages locale
	 * @return localized messages
	 */
	Messages getMessages(String name, Locale locale);
}

