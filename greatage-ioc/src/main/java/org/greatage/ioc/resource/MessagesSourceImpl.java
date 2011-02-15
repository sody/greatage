/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.resource;

import org.greatage.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * This class represents default {@link MessagesSource} implementation that uses {@link ResourceLocator} service to
 * locate message bundles, reads them in UTF-8 encoding and creates {@link MessagesImpl} instance as result.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MessagesSourceImpl extends AbstractMessagesSource {
	private static final String DEFAULT_CHARSET = "UTF-8";
	private static final int DEFAULT_BUFFER_SIZE = 2000;

	private final ResourceLocator resourceLocator;

	/**
	 * Creates new instance of message source that uses {@link ResourceLocator} service to locate message bundles, reads
	 * them in UTF-8 encoding and creates {@link MessagesImpl} instance as result.
	 *
	 * @param resourceLocator resource locator
	 */
	public MessagesSourceImpl(final ResourceLocator resourceLocator) {
		this.resourceLocator = resourceLocator;
	}

	/**
	 * {@inheritDoc} It uses {@link ResourceLocator} service to locate message bundles, reads them in UTF-8 encoding and
	 * creates {@link MessagesImpl} instance as result.
	 *
	 * @throws IllegalStateException if message bundle is not found
	 * @throws RuntimeException	  when error occurs while reading properties file
	 */
	public Messages getMessages(final String name, final Locale locale) {
		final Resource resource = resourceLocator.getResource(name + ".properties").inLocale(locale);
		if (resource != null) {
			final Map<String, String> properties = readProperties(resource);
			return new MessagesImpl(locale, properties);
		}
		throw new IllegalStateException(String.format("Can't find messages for %s", name));
	}

	/**
	 * Gets string properties from resource. Resource must contain properties in UTF-8 encoding.
	 *
	 * @param resource resource
	 * @return string properties from resource
	 * @throws RuntimeException when error occurs while reading properties file
	 */
	private Map<String, String> readProperties(final Resource resource) {
		Reader reader = null;
		final StringBuilder builder;
		try {
			reader = new InputStreamReader(resource.open(), DEFAULT_CHARSET);
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
