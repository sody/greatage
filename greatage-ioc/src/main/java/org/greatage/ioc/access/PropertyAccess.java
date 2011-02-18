package org.greatage.ioc.access;

/**
 * This interface represents helper that simplifies bean's property resolving mechanism. It provides set of methods for
 * resolving all property attributes, such as readable, writable, name, type. It also provides functionality to set and
 * get property value from specified bean instance.
 *
 * @param <T> bean type
 * @author Ivan Khalopik
 * @since 1.1
 */
public interface PropertyAccess<T> {

	/**
	 * Gets class access instance for configured bean.
	 *
	 * @return class access instance, not null
	 */
	ClassAccess<T> getClassAccess();

	/**
	 * Gets property name.
	 *
	 * @return property name, not null
	 */
	String getName();

	/**
	 * Gets property type.
	 *
	 * @return property type, not null
	 */
	Class getType();

	/**
	 * Checks if property has read access.
	 *
	 * @return true if property is readable, false otherwise
	 */
	boolean isReadable();

	/**
	 * Checks if property has write access.
	 *
	 * @return true if property is writable, false otherwise
	 */
	boolean isWritable();

	/**
	 * Gets bean property value.
	 *
	 * @param instance bean instance
	 * @return bean property value
	 * @throws PropertyAccessException if property has no read access or error occurs while getting value
	 */
	Object get(T instance);

	/**
	 * Sets bean property value.
	 *
	 * @param instance bean instance
	 * @param value	property value
	 * @throws PropertyAccessException if property has no write access or error occurs while setting value
	 */
	void set(T instance, Object value);
}
