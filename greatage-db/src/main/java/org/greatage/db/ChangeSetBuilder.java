package org.greatage.db;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ChangeSetBuilder {

	ChangeSetBuilder comment(String comment);

	ChangeSetBuilder context(String... context);

	InsertBuilder insert(String entityName);

	UpdateBuilder update(String entityName);

	DeleteBuilder delete(String entityName);

	void end();

	interface InsertBuilder extends ChangeBuilder {

		InsertBuilder set(String propertyName, Object value);
	}

	interface UpdateBuilder extends ChangeBuilder {

		UpdateBuilder set(String propertyName, Object value);

		ConditionEntryBuilder<UpdateBuilder> where(String property);
	}


	interface DeleteBuilder extends ChangeBuilder {

		ConditionEntryBuilder<DeleteBuilder> where(String propertyName);
	}

	interface ConditionBuilder<T extends ChangeBuilder> extends ChildBuilder<T> {

		ConditionEntryBuilder<T> and(String propertyName);
	}

	interface ConditionEntryBuilder<T extends ChangeBuilder> {

		ConditionBuilder<T> greaterThan(Object value);

		ConditionBuilder<T> greaterOrEqual(Object value);

		ConditionBuilder<T> lessThan(Object value);

		ConditionBuilder<T> lessOrEqual(Object value);

		ConditionBuilder<T> equal(Object value);

		ConditionBuilder<T> notEqual(Object value);
	}

	interface ChangeBuilder extends ChildBuilder<ChangeSetBuilder> {
	}

	interface ChildBuilder<T> {

		T end();
	}
}
