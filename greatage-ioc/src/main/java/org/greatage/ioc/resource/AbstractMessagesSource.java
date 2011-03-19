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
