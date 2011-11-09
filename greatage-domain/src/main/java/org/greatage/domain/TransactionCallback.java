package org.greatage.domain;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface TransactionCallback<V, T> {

	V doInTransaction(T transaction) throws Exception;
}
