package org.greatage.db;

import org.greatage.util.EncodeUtils;
import org.greatage.util.StringUtils;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CheckSumUtils {
	private static final String DEFAULT_ALGORITHM = "MD5";

	public static String compositeCheckSum(final String compositeCheckSum) {
		final byte[] encoded = EncodeUtils.encode(compositeCheckSum.getBytes(), DEFAULT_ALGORITHM);
		return DEFAULT_ALGORITHM + ":" + StringUtils.toHexString(encoded) + ";";
	}

	public static boolean isValid(final String checkSum) {
		return checkSum != null && checkSum.startsWith(DEFAULT_ALGORITHM);
	}
}
