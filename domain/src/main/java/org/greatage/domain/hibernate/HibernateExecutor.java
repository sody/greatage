/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.hibernate;

import org.greatage.domain.EntityTransactionManager;


/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface HibernateExecutor extends EntityTransactionManager {

	<T> T execute(HibernateCallback<T> callback);

}
