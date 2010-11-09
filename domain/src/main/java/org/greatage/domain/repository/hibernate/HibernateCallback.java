/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository.hibernate;

import org.hibernate.Session;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public interface HibernateCallback<T> {

	T doInSession(Session session) throws Throwable;

}
