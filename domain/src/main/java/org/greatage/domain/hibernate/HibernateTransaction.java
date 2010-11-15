/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.hibernate;

import org.greatage.domain.Transaction;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HibernateTransaction implements Transaction {
	private final org.hibernate.Transaction transaction;

	public HibernateTransaction(final org.hibernate.Transaction transaction) {
		this.transaction = transaction;
	}

	public void commit() {
		transaction.commit();
	}

	public void rollback() {
		transaction.rollback();
	}
}
