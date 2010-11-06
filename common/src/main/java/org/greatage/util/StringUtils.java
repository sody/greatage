package org.greatage.util;

/**
 * This class represents utility methods for working with java strings.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class StringUtils {

	private static final char[] HEX_SYMBOLS = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
	};

	/**
	 * Checks if char sequence is empty(null or has no length).
	 *
	 * @param sequence sequence to check empty state
	 * @return true if sequence is empty, false otherwise
	 */
	public static boolean isEmpty(final CharSequence sequence) {
		return sequence == null || sequence.length() <= 0;
	}

	/**
	 * Converts binary data to string hex representation
	 *
	 * @param data binary data
	 * @return binary data hex representation
	 */
	public static String toHexString(final byte[] data) {
		final StringBuilder builder = new StringBuilder(2 * data.length);
		for (byte item : data) {
			builder.append(HEX_SYMBOLS[(0xf0 & item) >>> 4]);
			builder.append(HEX_SYMBOLS[(0x0f & item)]);
		}
		return builder.toString();
	}
}
