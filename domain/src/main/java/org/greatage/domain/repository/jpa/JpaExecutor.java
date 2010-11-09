/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository.jpa;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public interface JpaExecutor {

	<T> T execute(JpaCallback<T> callback);

}
