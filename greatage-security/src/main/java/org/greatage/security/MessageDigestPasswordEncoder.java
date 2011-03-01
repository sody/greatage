/*
 * Copyright 2011 Ivan Khalopik
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
