package org.greatage.db;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ChangeLog {

    void update(ChangeLogSupport changeLog, String... context);

    ChangeLog dropFirst();

    ChangeLog clearCheckSums();

    ChangeLog context(String... context);

    ChangeSet changeSet(String id);

    interface ChangeSet {

        ChangeSet author(String author);

        ChangeSet location(String location);

        ChangeSet comment(String comment);

        ChangeSet context(String... context);

        Trick trick();
    }
}
