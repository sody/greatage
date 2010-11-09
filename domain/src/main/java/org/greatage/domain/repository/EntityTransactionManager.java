/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public interface EntityTransactionManager {

	void begin();

	void commit();

	void rollback();

}
