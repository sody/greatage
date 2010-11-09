/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository.hibernate;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public interface HibernateExecutor {

	<T> T execute(HibernateCallback<T> callback);

}
