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

package org.greatage.util;

/**
 * This class represent utility that locks some code to prevent use it again.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Locker {
	private boolean lock;

	/**
	 * Checks if instance is locked.
	 *
	 * @throws IllegalStateException if instance is locked
	 */
	public synchronized void check() {
		if (lock) {
			throw new IllegalStateException("Locked");
		}
	}

	/**
	 * Locks instance to prevent use it again.
	 *
	 * @throws IllegalStateException if instance is locked
	 */
	public synchronized void lock() {
		check();
		lock = true;
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("locked", lock);
		return builder.toString();
	}
}
