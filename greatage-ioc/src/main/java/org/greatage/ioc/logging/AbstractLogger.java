/*
 * Copyright 2011 Ivan Khalopik
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

package org.greatage.ioc.logging;

import org.greatage.util.DescriptionBuilder;

/**
 * This class represent abstract logger implementation that delegates log methods without exception parameter to those
 * with this parameter.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractLogger implements Logger {

	/**
	 * {@inheritDoc}
	 */
	public void trace(final String format, final Object... parameters) {
		trace(null, format, parameters);
	}

	/**
	 * {@inheritDoc}
	 */
	public void debug(final String format, final Object... parameters) {
		debug(null, format, parameters);
	}

	/**
	 * {@inheritDoc}
	 */
	public void info(final String format, final Object... parameters) {
		info(null, format, parameters);
	}

	/**
	 * {@inheritDoc}
	 */
	public void warn(final String format, final Object... parameters) {
		warn(null, format, parameters);
	}

	/**
	 * {@inheritDoc}
	 */
	public void error(final String format, final Object... parameters) {
		error(null, format, parameters);
	}

	/**
	 * Gets logger name.
	 *
	 * @return logger name
	 */
	protected abstract String getName();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append(getName());
		return builder.toString();
	}
}
