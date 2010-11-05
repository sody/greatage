package org.greatage.security.context;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface PermissionResolver {

	boolean isGranted(Object securedObject, String permission);

}
