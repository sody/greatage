/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Transaction {

	void commit();

	void rollback();

}
