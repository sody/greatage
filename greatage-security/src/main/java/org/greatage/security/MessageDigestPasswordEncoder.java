/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.security;

import org.greatage.util.EncodeUtils;
import org.greatage.util.StringUtils;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MessageDigestPasswordEncoder implements PasswordEncoder {
	private final String algorithm;
	private final boolean useBase64;

	public MessageDigestPasswordEncoder(final String algorithm, final boolean useBase64) {
		this.algorithm = algorithm;
		this.useBase64 = useBase64;
	}

	public String encode(final String password) {
		if (password == null) {
			return null;
		}
		final byte[] bytes = EncodeUtils.encode(password.getBytes(), algorithm);
		return useBase64 ? new String(EncodeUtils.encodeBase64(bytes)) : StringUtils.toHexString(bytes);
	}
}
