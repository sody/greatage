package org.greatage.db;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ConditionEntryBuilder<T extends ChangeBuilder> {

	ConditionBuilder<T> greaterThan(Object value);

	ConditionBuilder<T> greaterOrEqual(Object value);

	ConditionBuilder<T> lessThan(Object value);

	ConditionBuilder<T> lessOrEqual(Object value);

	ConditionBuilder<T> equal(Object value);

	ConditionBuilder<T> notEqual(Object value);
}
