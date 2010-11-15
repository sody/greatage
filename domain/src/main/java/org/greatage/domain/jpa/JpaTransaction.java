/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.jpa;

import org.greatage.domain.Transaction;

import javax.persistence.EntityTransaction;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JpaTransaction implements Transaction {
	private final EntityTransaction transaction;

	public JpaTransaction(final EntityTransaction transaction) {
		this.transaction = transaction;
	}

	public void commit() {
		transaction.commit();
	}

	public void rollback() {
		transaction.rollback();
	}
}
