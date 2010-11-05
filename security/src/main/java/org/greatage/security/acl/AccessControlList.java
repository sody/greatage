package org.greatage.security.acl;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface AccessControlList {

	Object getSecuredObject();

	AccessControlEntry getAccessControlEntry(String authority, String permission);

}
