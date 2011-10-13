package org.greatage.db;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Database {

	void update(ChangeLog changeLog, String... context);

	ChangeSetBuilder changeSet(String id, String author, String location);

	UpdateOptions options();

	public interface UpdateOptions {

		UpdateOptions dropFirst();

		UpdateOptions clearCheckSums();

		UpdateOptions context(String... context);

		void update(ChangeLog changeLog);
	}
}
