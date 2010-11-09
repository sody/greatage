/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository.hibernate;

import org.greatage.domain.repository.EntityTransactionManager;


/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public interface HibernateExecutor extends EntityTransactionManager {

	<T> T execute(HibernateCallback<T> callback);

}
