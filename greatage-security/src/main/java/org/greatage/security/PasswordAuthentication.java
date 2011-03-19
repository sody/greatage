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

import org.greatage.util.CollectionUtils;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class PasswordAuthentication implements Authentication {
	private final String name;
	private final List<String> authorities;
	private final String encodedPassword;

	public PasswordAuthentication(final String name, final String encodedPassword, final List<String> authorities) {
		this.name = name;
		this.authorities = authorities;
		this.encodedPassword = encodedPassword;
	}

	public PasswordAuthentication(final String name, final String encodedPassword, final String... authorities) {
		this(name, encodedPassword, CollectionUtils.newList(authorities));
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return encodedPassword;
	}

	public List<String> getAuthorities() {
		return authorities;
	}
}
