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

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AnonymousProvider extends AbstractAuthenticationProvider<Authentication, AnonymousToken> {
	public AnonymousProvider() {
		super(Authentication.class, AnonymousToken.class);
	}

	@Override
	protected Authentication doSignIn(final AnonymousToken token) {
		return getAntonymousAuthentication();
	}

	@Override
	protected void doSignOut(final Authentication authentication) {
		//do nothing by default
	}

	protected User getAntonymousAuthentication() {
		return new User("anonymous", CollectionUtils.<String, String>newList(AuthorityConstants.STATUS_ANONYMOUS));
	}
}
