package org.greatage.resource;

import org.greatage.util.I18nUtils;
import org.greatage.util.StringUtils;

import java.io.InputStream;
import java.util.Locale;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class DefaultMessagesSource extends AbstractMessagesSource {

	public Messages getMessages(String name, Locale locale) {
		for (Locale candidateLocale : I18nUtils.getCandidateLocales(locale)) {
			final String resourceName = toResourceName(name, candidateLocale);
			final InputStream stream = ClassLoader.getSystemResourceAsStream(resourceName);
			if (stream != null) {
				return new MapMessages(locale, I18nUtils.getProperties(stream));
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
}
