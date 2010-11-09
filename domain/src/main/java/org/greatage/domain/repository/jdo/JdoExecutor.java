/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository.jdo;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public interface JdoExecutor {

	<T> T execute(JdoCallback<T> callback);

}
