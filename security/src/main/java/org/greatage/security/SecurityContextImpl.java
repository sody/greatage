/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
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
		loggedUsers.put(user.getName(), user);
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
