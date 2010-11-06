/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.hibernate;

import org.hibernate.proxy.HibernateProxy;

/**
 * @author Ivan Khalopik
 */
public abstract class HibernateUtils {

	/**
	 * Get the true, underlying class of a proxied persistent class.
	 *
	 * @param entityClass proxied persistent class
	 * @param <T>         type of entity
	 * @return true, underlying class of a proxied persistent class
	 */
	@SuppressWarnings({"unchecked"})
	public static <T> Class<T> getRealClass(Class<T> entityClass) {
		if (HibernateProxy.class.isAssignableFrom(entityClass)) {
			return (Class<T>) entityClass.getSuperclass();
		}
		return entityClass;
	}

}
