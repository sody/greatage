package org.greatage.db;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Database {

	void update(ChangeLog changeLog, String... context);

	ChangeSetBuilder changeSet(String id, String author, String location);

}
