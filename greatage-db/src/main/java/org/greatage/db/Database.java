package org.greatage.db;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Database {

	void update(ChangeLog changeLog, String... context);

	Options options();

	ChangeSet changeSet(String id);

	interface Options {

		Options dropFirst();

		Options clearCheckSums();

		Options context(String... context);

		void update(ChangeLog changeLog);
	}

	interface ChangeSet {

		ChangeSet author(String author);

		ChangeSet location(String location);

		ChangeSet comment(String comment);

		ChangeSet context(String... context);

		Trick trick();
	}

	interface Callback {

		void doWithTricks(Trick trick);
	}
}
