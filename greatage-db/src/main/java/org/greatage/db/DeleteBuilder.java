package org.greatage.db;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface DeleteBuilder extends ChangeBuilder {

	ConditionEntryBuilder<DeleteBuilder> where(String propertyName);

}
