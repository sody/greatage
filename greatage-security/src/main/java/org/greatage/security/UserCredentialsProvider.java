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

package org.greatage.security;

import org.greatage.util.StringUtils;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class UserCredentialsProvider extends AbstractAuthenticationProvider<User, Credentials> {
	private final SecretEncoder secretEncoder;
	private final String credentialsType;

	protected UserCredentialsProvider() {
		this(null, Credentials.DEFAULT_TYPE);
	}

	protected UserCredentialsProvider(final String credentialsType) {
		this(null, credentialsType);
	}

	protected UserCredentialsProvider(final SecretEncoder secretEncoder) {
		this(secretEncoder, Credentials.DEFAULT_TYPE);
	}

	protected UserCredentialsProvider(final SecretEncoder secretEncoder, final String credentialsType) {
		super(User.class, Credentials.class);
		this.secretEncoder = secretEncoder;
		this.credentialsType = credentialsType;
	}

	@Override
	protected boolean supports(final AuthenticationToken token) {
		return super.supports(token) && credentialsType.equals(((Credentials) token).getType());
	}

	@Override
	protected User doSignIn(final Credentials token) {
		if (StringUtils.isEmpty(token.getKey()) || StringUtils.isEmpty(token.getSecret())) {
			throw new AuthenticationException("Invalid credentials");
		}
		final String encodedSecret = secretEncoder != null ? secretEncoder.encode(token.getSecret()) : token.getSecret();
		final User authentication = getAuthentication(token.getKey(), encodedSecret);
		if (authentication == null) {
			throw new AuthenticationException("Invalid credentials");
		}
		return authentication;
	}

	@Override
	protected void doSignOut(final User authentication) {
		//do nothing by default
	}

	protected abstract User getAuthentication(final String key, final String secret);
}
