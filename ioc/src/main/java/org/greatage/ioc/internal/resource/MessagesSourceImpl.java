/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.internal.resource;

import org.greatage.ioc.services.Messages;
import org.greatage.util.CollectionUtils;
import org.greatage.util.I18nUtils;
import org.greatage.util.StringUtils;

import java.io.*;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MessagesSourceImpl extends AbstractMessagesSource {
	private static final String DEFAULT_CHARSET = "UTF-8";
	private static final int DEFAULT_BUFFER_SIZE = 2000;

	private final ClassLoader classLoader;

	public MessagesSourceImpl() {
		this(ClassLoader.getSystemClassLoader());
	}

	public MessagesSourceImpl(final ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public Messages getMessages(final String name, final Locale locale) {
		for (Locale candidateLocale : I18nUtils.getCandidateLocales(locale)) {
			final String resourceName = toResourceName(name, candidateLocale);
			final InputStream stream = classLoader.getResourceAsStream(resourceName);
			if (stream != null) {
				return new MessagesImpl(locale, getProperties(stream));
			}
		}
		throw new RuntimeException(String.format("Can't find messages for %s", name));
	}

	private static String toResourceName(String name, Locale locale) {
		final StringBuilder sb = new StringBuilder(name.replace('.', '/'));
		if (!StringUtils.isEmpty(locale.toString())) {
			sb.append("_").append(locale);
		}
		sb.append('.').append("properties");
		return sb.toString();
	}

	/**
	 * Gets string properties from input stream. Input stream must contain properties in UTF-8 encoding.
	 *
	 * @param inputStream inputStream
	 * @return string properties from input stream
	 */
	private Map<String, String> getProperties(final InputStream inputStream) {
		Reader reader = null;
		final StringBuilder builder;
		try {
			reader = new InputStreamReader(inputStream, DEFAULT_CHARSET);
			builder = new StringBuilder(DEFAULT_BUFFER_SIZE);
			final char[] buffer = new char[DEFAULT_BUFFER_SIZE];

			int length;
			do {
				length = reader.read(buffer);
				for (int i = 0; i < length; i++) {
					final char ch = buffer[i];

					if (ch <= '\u007f') {
						builder.append(ch);
					} else {
						builder.append(String.format("\\u%04x", (int) ch));
					}
				}
			} while (length > 0);
			final Properties properties = new Properties();
			properties.load(new ByteArrayInputStream(builder.toString().getBytes()));

			final Map<String, String> result = CollectionUtils.newMap();
			for (Object property : properties.keySet()) {
				final String key = String.valueOf(property);
				result.put(key, properties.getProperty(key));
			}
			return result;
		} catch (IOException e) {
			throw new RuntimeException("Can't load messages from input stream", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ex) {
					// Ignore.
				}
			}
		}
	}
}
