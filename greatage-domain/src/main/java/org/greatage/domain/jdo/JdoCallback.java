/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.jdo;

import javax.jdo.PersistenceManager;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface JdoCallback<T> {

	T doInJdo(PersistenceManager pm) throws Throwable;

}
