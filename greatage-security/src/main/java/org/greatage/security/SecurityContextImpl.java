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
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SecurityContextImpl implements SecurityContext {
	private final ThreadLocal<Authentication> currentUser = new ThreadLocal<Authentication>();
	private final Map<String, Authentication> loggedUsers = CollectionUtils.newConcurrentMap();

	public Authentication getCurrentUser() {
		return currentUser.get();
	}

	public void setCurrentUser(final Authentication currentUser) {
		if (currentUser == null) {
			final Authentication user = getCurrentUser();
			if (user != null) {
				loggedUsers.remove(user.getName());
			}
		} else {
			loggedUsers.put(currentUser.getName(), currentUser);
		}
		this.currentUser.set(currentUser);
	}

	public List<Authentication> getLoggedUsers() {
		return CollectionUtils.newList(loggedUsers.values());
	}
}
