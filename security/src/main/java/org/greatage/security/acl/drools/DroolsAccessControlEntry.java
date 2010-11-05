package org.greatage.security.acl.drools;

import org.greatage.security.acl.AccessControlEntry;

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
