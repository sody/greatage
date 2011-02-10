/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.jpa;

import org.greatage.domain.TransactionExecutor;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface JpaExecutor extends TransactionExecutor {

	<T> T execute(JpaCallback<T> callback);

}
