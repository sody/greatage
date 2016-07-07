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

package org.greatage.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class represents utility for encoding data.
 *
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
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/',
	};
	private static final int BASE64_MASK = 0x3f;

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
			result[resultCursor++] = BASE64_DICTIONARY[(byte0 << 4) & BASE64_MASK | (byte1 >> 4)];
			result[resultCursor++] = BASE64_DICTIONARY[(byte1 << 2) & BASE64_MASK | (byte2 >> 6)];
			result[resultCursor++] = BASE64_DICTIONARY[byte2 & BASE64_MASK];
		}

		switch (data.length - dataCursor) {
			case 1:
				final byte b = data[dataCursor];
				result[resultCursor++] = BASE64_DICTIONARY[b >> 2];
				result[resultCursor++] = BASE64_DICTIONARY[(b << 4) & BASE64_MASK];
				result[resultCursor++] = BASE64_NULL;
				result[resultCursor] = BASE64_NULL;
				break;
			case 2:
				final byte b0 = data[dataCursor++];
				final byte b1 = data[dataCursor];
				result[resultCursor++] = BASE64_DICTIONARY[b0 >> 2];
				result[resultCursor++] = BASE64_DICTIONARY[(b0 << 4) & BASE64_MASK | (b1 >> 4)];
				result[resultCursor++] = BASE64_DICTIONARY[(b1 << 2) & BASE64_MASK];
				result[resultCursor] = BASE64_NULL;
				break;
		}
		return result;
	}
}
