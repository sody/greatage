package org.greatage.resource;

import org.greatage.util.DescriptionBuilder;

import java.util.Locale;

/**
 * This class represents abstract implementation of {@link Messages} returns placeholder if doesn't
 * contain localized string key and uses {@link String#format(java.util.Locale, String, Object...)} for formatting.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractMessages implements Messages {
	private static final String MISSING_KEY_PLACEHOLDER = "[[missing key: %s]]";
	private final Locale locale;

	/**
	 * Creates new instance of messages for specified locale. This locale will be used for formatting.
	 *
	 * @param locale messages locale
	 */
	protected AbstractMessages(final Locale locale) {
		this.locale = locale;
	}

	public boolean contains(final String key) {
		return getMessage(key) != null;
	}

	public String get(final String key) {
		return contains(key) ? getMessage(key) : String.format(MISSING_KEY_PLACEHOLDER, key);
	}

	public String format(final String key, final Object... parameters) {
		final String format = get(key);
		return String.format(locale, format, parameters);
	}

	/**
	 * Gets localized string from storage place.
	 *
	 * @param key localized string key
	 * @return localized string or null if doesn't contain key
	 */
	protected abstract String getMessage(final String key);

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(Messages.class);
		builder.append(locale);
		return builder.toString();
	}
}
