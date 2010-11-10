/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.jpa;

import org.greatage.domain.EntityTransactionManager;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface JpaExecutor extends EntityTransactionManager {

	<T> T execute(JpaCallback<T> callback);

}
