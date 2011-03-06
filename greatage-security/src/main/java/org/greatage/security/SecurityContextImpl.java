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
public class SecurityContextImpl<T extends Authentication> implements SecurityContext<T> {
	private final ThreadLocal<T> currentUser = new ThreadLocal<T>();
	private final Map<String, T> loggedUsers = CollectionUtils.newConcurrentMap();

	public T getCurrentUser() {
		return currentUser.get();
	}

	public T getUser(final String name) {
		return loggedUsers.get(name);
	}

	public List<T> getLoggedUsers() {
		return CollectionUtils.newList(loggedUsers.values());
	}

	public void initCurrentUser(final T user) {
		if (user != null) {
			loggedUsers.put(user.getName(), user);
		}
		currentUser.set(user);
	}

	public void clearCurrentUser() {
		currentUser.set(null);
	}

	public void removeCurrentUser() {
		final T user = currentUser.get();
		if (user != null) {
			loggedUsers.remove(user.getName());
		}
		currentUser.set(null);
	}
}
