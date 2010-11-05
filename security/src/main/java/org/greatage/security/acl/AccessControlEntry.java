package org.greatage.security.acl;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface AccessControlEntry {

	Object getSecuredObject();

	String getAuthority();

	String getPermission();

	boolean isGranted();

}
