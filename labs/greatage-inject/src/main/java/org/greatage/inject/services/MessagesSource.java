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

package org.greatage.inject.services;

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
	 * @param clazz  messages class, not {@code null}
	 * @param locale messages locale, if {@code null} then root locale will be used
	 * @return localized messages or {@code null} if not found
	 */
	Messages getMessages(Class clazz, Locale locale);

	/**
	 * Creates or retrieves localized messages in specified locale based on specified name.
	 *
	 * @param name   messages name, not {@code null}
	 * @param locale messages locale, if {@code null} then root locale will be used
	 * @return localized messages or {@code null} if not found
	 */
	Messages getMessages(String name, Locale locale);
}

