package org.greatage.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class EncodeUtils {
	private static final byte BASE64_NULL = '=';

	private static final byte BASE64_DICTIONARY[] = {
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
			'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
			'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
	};

	/**
	 * Encodes binary data using specified message digest algorithm.
	 *
	 * @param data	  binary data
	 * @param algorithm message digest algorithm
	 * @return encoded binary data
	 * @throws IllegalArgumentException if specified algorithm not found
	 */
	public static byte[] encode(final byte[] data, final String algorithm) {
		try {
			final MessageDigest digest = MessageDigest.getInstance(algorithm);
			return digest.digest(data);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(String.format("Unknown digest algorithm: '%s'", algorithm));
		}
	}

	/**
	 * Encodes binary data using Base64 algorithm.
	 *
	 * @param data binary data
	 * @return encoded binary data
	 */
	public static byte[] encodeBase64(final byte[] data) {
		final int tripleCount = data.length / 3;
		final int resultLength = data.length % 3 == 0 ? 4 * tripleCount : 4 * (tripleCount + 1);
		final byte[] result = new byte[resultLength];

		int dataCursor = 0, resultCursor = 0;
		for (int i = 0; i < tripleCount; i++) {
			final byte byte0 = data[dataCursor++];
			final byte byte1 = data[dataCursor++];
			final byte byte2 = data[dataCursor++];
			result[resultCursor++] = BASE64_DICTIONARY[byte0 >> 2];
			result[resultCursor++] = BASE64_DICTIONARY[(byte0 << 4) & 0x3f | (byte1 >> 4)];
			result[resultCursor++] = BASE64_DICTIONARY[(byte1 << 2) & 0x3f | (byte2 >> 6)];
			result[resultCursor++] = BASE64_DICTIONARY[byte2 & 0x3f];
		}

		switch (data.length - dataCursor) {
			case 1:
				final byte b = data[dataCursor];
				result[resultCursor++] = BASE64_DICTIONARY[b >> 2];
				result[resultCursor++] = BASE64_DICTIONARY[(b << 4) & 0x3f];
				result[resultCursor++] = BASE64_NULL;
				result[resultCursor] = BASE64_NULL;
				break;
			case 2:
				final byte b0 = data[dataCursor++];
				final byte b1 = data[dataCursor];
				result[resultCursor++] = BASE64_DICTIONARY[b0 >> 2];
				result[resultCursor++] = BASE64_DICTIONARY[(b0 << 4) & 0x3f | (b1 >> 4)];
				result[resultCursor++] = BASE64_DICTIONARY[(b1 << 2) & 0x3f];
				result[resultCursor] = BASE64_NULL;
				break;
		}
		return result;
	}
}
