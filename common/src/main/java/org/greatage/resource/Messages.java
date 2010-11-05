package org.greatage.resource;

/**
 * This class represents localized strings mapped by keys for particular locale.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Messages {

	/**
	 * Checks if messages contains localized string with specified key.
	 *
	 * @param key localized string key
	 * @return true if messages contains localized string, false otherwise
	 */
	boolean contains(String key);

	/**
	 * Gets localized string with specified key or placeholder if messages doesn't contain key.
	 *
	 * @param key localized string key
	 * @return localized string with specified key or placeholder
	 */
	String get(String key);

	/**
	 * Formats localized string with specified key using parameters.
	 *
	 * @param key		localized string key
	 * @param parameters format parameters
	 * @return formatted localized string
	 */
	String format(String key, Object... parameters);

}
