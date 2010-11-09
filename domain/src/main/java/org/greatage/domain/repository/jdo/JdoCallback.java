/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository.jdo;

import javax.jdo.PersistenceManager;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public interface JdoCallback<T> {

	T doInJdo(PersistenceManager pm) throws Throwable;

}
