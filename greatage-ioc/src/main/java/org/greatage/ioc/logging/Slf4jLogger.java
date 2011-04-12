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

package org.greatage.ioc.logging;

import org.greatage.util.DescriptionBuilder;

/**
 * This class represents logger implementation through slf4j logging API.
 *
 * @author Ivan Khalopik
 * @since 1.1
 */
public class Slf4jLogger implements Logger {
	private final org.slf4j.Logger delegate;

	/**
	 * Creates new logger instance through Slf4j logging API.
	 *
	 * @param logger Slf4j logger
	 */
	public Slf4jLogger(final org.slf4j.Logger logger) {
		delegate = logger;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return delegate.getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
	public void trace(final String format, final Object... parameters) {
		final LogData data = format(format, parameters);
		delegate.trace(data.getMessage(), data.getThrowable());
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
	public void debug(final String format, final Object... parameters) {
		final LogData data = format(format, parameters);
		delegate.debug(data.getMessage(), data.getThrowable());
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
	public void info(final String format, final Object... parameters) {
		final LogData data = format(format, parameters);
		delegate.info(data.getMessage(), data.getThrowable());
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
	public void warn(final String format, final Object... parameters) {
		final LogData data = format(format, parameters);
		delegate.warn(data.getMessage(), data.getThrowable());
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
	public void error(final String format, final Object... parameters) {
		final LogData data = format(format, parameters);
		delegate.error(data.getMessage(), data.getThrowable());
	}

	/**
	 * Formats log data from specified message format and parameters.
	 *
	 * @param format	 message format
	 * @param parameters parameters to format message with, the last parameter can be attached exception
	 * @return formatted log data
	 */
	protected LogData format(final String format, final Object... parameters) {
		if (parameters == null || parameters.length == 0) {
			return new LogData(format, null);
		}

		final int lastParameterIndex = parameters.length - 1;
		final Object lastParameter = parameters[lastParameterIndex];
		if (lastParameter instanceof Throwable) {
			final Object[] trimmed = new Object[lastParameterIndex];
			System.arraycopy(parameters, 0, trimmed, 0, lastParameterIndex);
			return new LogData(String.format(format, trimmed), (Throwable) lastParameter);
		}

		return new LogData(String.format(format, parameters), null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append(getName());
		return builder.toString();
	}

	/**
	 * This class represents log message data with attached exception
	 */
	class LogData {
		private final String message;
		private final Throwable throwable;

		/**
		 * Creates new instance of log message data with specified message text and attached exception.
		 *
		 * @param message   message text
		 * @param throwable attached exception
		 */
		LogData(String message, Throwable throwable) {
			this.message = message;
			this.throwable = throwable;
		}

		/**
		 * Gets log message, ready for use.
		 *
		 * @return log message
		 */
		public String getMessage() {
			return message;
		}

		/**
		 * Gets attached exception.
		 *
		 * @return attached exception or null
		 */
		public Throwable getThrowable() {
			return throwable;
		}
	}
}
