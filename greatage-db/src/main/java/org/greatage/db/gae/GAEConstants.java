package org.greatage.db.gae;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface GAEConstants {
    String LOG_TABLE = "DATABASE_CHANGE_LOG";
    String TITLE_COLUMN = "title";
    String AUTHOR_COLUMN = "author";
    String LOCATION_COLUMN = "location";
    String COMMENT_COLUMN = "comment";
    String CHECKSUM_COLUMN = "checkSum";
    String EXECUTED_AT_COLUMN = "executedAt";

    String LOCK_TABLE = "DATABASE_CHANGE_LOG_LOCK";
    String LOCKED_AT_COLUMN = "lockedAt";
    long LOCK_ID = 1l;
}
