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

}
