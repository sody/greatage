package org.greatage.ioc.access;

import java.util.Collection;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public interface ClassAccess {

	Collection<String> getPropertyNames();

	Class getType();

	PropertyAccess getPropertyAccess(String propertyName);

	Object get(Object instance, String propertyName);

	void set(Object instance, String propertyName, Object value);
}
