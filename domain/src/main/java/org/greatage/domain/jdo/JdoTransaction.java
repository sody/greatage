/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.jdo;

import org.greatage.domain.Transaction;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JdoTransaction implements Transaction {
	private final javax.jdo.Transaction transaction;

	public JdoTransaction(final javax.jdo.Transaction transaction) {
		this.transaction = transaction;
	}

	public void commit() {
		transaction.commit();
	}

	public void rollback() {
		transaction.rollback();
	}
}
