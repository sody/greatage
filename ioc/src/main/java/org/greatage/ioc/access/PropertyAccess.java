package org.greatage.ioc.access;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public interface PropertyAccess {

	ClassAccess getClassAccess();

	String getName();

	Class getType();

	boolean isReadable();

	boolean isWritable();

	Object get(Object instance);

	void set(Object instance, Object value);
}
