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

package org.greatage.security.drools;

import org.greatage.security.AccessControlEntry;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class DroolsAccessControlEntry implements AccessControlEntry {
	private final String authority;
	private final String permission;
	private final Object object;

	private boolean granted;

	public DroolsAccessControlEntry(final Object object, final String authority, final String permission) {
		this.authority = authority;
		this.permission = permission;
		this.object = object;
	}

	public String getAuthority() {
		return authority;
	}

	public String getPermission() {
		return permission;
	}

	public Object getSecuredObject() {
		return object;
	}

	public boolean isGranted() {
		return granted;
	}

	public void grant() {
		granted = true;
	}

	public void deny() {
		granted = false;
	}

}
