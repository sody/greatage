/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.jdo;

import org.greatage.domain.TransactionExecutor;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface JdoExecutor extends TransactionExecutor {

	<T> T execute(JdoCallback<T> callback);

}
