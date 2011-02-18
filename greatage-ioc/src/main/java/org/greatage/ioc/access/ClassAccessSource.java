package org.greatage.ioc.access;

/**
 * This interface represents service that simplifies bean's property resolving mechanism.
 *
 * @author Ivan Khalopik
 * @since 1.1
 */
public interface ClassAccessSource {

	/**
	 * Gets class access instance for specified class.
	 *
	 * @param clazz bean class, not null
	 * @param <T>   bean type
	 * @return class access instance for specified class, not null
	 * @throws PropertyAccessException when error occurs while getting class access
	 */
	<T> ClassAccess<T> getAccess(Class<T> clazz);
}
