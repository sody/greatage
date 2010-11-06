/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.util;

import java.io.*;
import java.util.*;

/**
 * This class represents utility methods for working with localization and internationalization.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class I18nUtils {
	public static final Locale ROOT_LOCALE = new Locale("");

	private static final String DEFAULT_CHARSET = "UTF-8";
	private static final int DEFAULT_BUFFER_SIZE = 2000;

	/**
	 * Gets locale for specified string value.
	 *
	 * @param value string value to convert
	 * @return converted from string locale or null if can not be converted
	 */
	public static Locale getLocale(final String value) {
		if (value != null) {
			final StringTokenizer tokens = new StringTokenizer(value, "_");
			final String language = tokens.hasMoreTokens() ? tokens.nextToken() : "";
			final String country = tokens.hasMoreTokens() ? tokens.nextToken() : "";
			String variant = "";
			String sep = "";
			while (tokens.hasMoreTokens()) {
				variant += sep + tokens.nextToken();
				sep = "_";
			}
			return new Locale(language, country, variant);
		}
		return null;
	}

	/**
	 * Gets locale candidates for specified locale.
	 *
	 * @param locale locale
	 * @return locale candidates for specified locale
	 */
	public static List<Locale> getCandidateLocales(final Locale locale) {
		final List<Locale> locales = CollectionUtils.newList();
		if (locale != null) {
			final String language = locale.getLanguage();
			final String country = locale.getCountry();
			final String variant = locale.getVariant();

			if (variant.length() > 0) {
				locales.add(locale);
			}
			if (country.length() > 0) {
				locales.add((locales.size() == 0) ?
						locale : new Locale(language, country));
			}
			if (language.length() > 0) {
				locales.add((locales.size() == 0) ?
						locale : new Locale(language));
			}
		}
		locales.add(ROOT_LOCALE);
		return locales;
	}

	/**
	 * Gets string properties from input stream. Input stream must contain properties in UTF-8 encoding.
	 *
	 * @param inputStream inputStream
	 * @return string properties from input stream
	 */
	public static Map<String, String> getProperties(final InputStream inputStream) {
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
