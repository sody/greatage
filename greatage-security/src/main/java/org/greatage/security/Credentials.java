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

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Credentials implements AuthenticationToken {
	public static final String DEFAULT_TYPE = "credentials";

	private final String type;
	private final String key;
	private final String secret;

	public Credentials(final String key, final String secret) {
		this(DEFAULT_TYPE, key, secret);
	}

	public Credentials(final String type, final String key, final String secret) {
		this.type = type;
		this.key = key;
		this.secret = secret;
	}

	public String getType() {
		return type;
	}

	public String getKey() {
		return key;
	}

	public String getSecret() {
		return secret;
	}
}
