package org.greatage.db.gae;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import org.greatage.db.ChangeLog;
import org.greatage.db.internal.CheckSumUtils;
import org.greatage.db.DatabaseException;
import org.greatage.util.DescriptionBuilder;
import org.greatage.util.StringUtils;

import java.util.*;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAEChangeSet implements ChangeLog.ChangeSet, GAEConstants {
    private final List<GAEChange> changes = new ArrayList<GAEChange>();

    private final String title;
    private final String location;

    private String author;
    private String comment;
    private List<String> context;

    GAEChangeSet(final String title, final String location, final String author) {
        this.title = title;
        this.location = location;
        this.author = author;
    }

    public ChangeLog.ChangeSet author(final String author) {
        this.author = author;
        return this;
    }

    public ChangeLog.ChangeSet comment(final String comment) {
        this.comment = comment;
        return this;
    }

    public ChangeLog.ChangeSet context(final String... context) {
        this.context = Arrays.asList(context);
        return this;
    }

    void addChange(final GAEChange change) {
        changes.add(change);
    }

    void execute(final DatastoreService store, final Collection<String> context, final boolean clearCheckSum) {
        if (supports(context)) {
            final String description = toString();
            final String checkSum = CheckSumUtils.calculateCheckSum(description);
            System.out.println("Executing ChangeSet: " + description);
            System.out.println("CheckSum : " + checkSum);

            final Entity logEntry = (Entity) new GAESelect(LOG_TABLE)
                    .where(new GAEConditionEntry(TITLE_COLUMN).eq(title)
                            .and(new GAEConditionEntry(AUTHOR_COLUMN).eq(author))
                            .and(new GAEConditionEntry(LOCATION_COLUMN).eq(location)))
                    .unique()
                    .get(store);

            if (logEntry != null) {
                final String expectedCheckSum = (String) logEntry.getProperty(CHECKSUM_COLUMN);
                if (clearCheckSum || !CheckSumUtils.isValid(expectedCheckSum)) {
                    new GAEUpdate(LOG_TABLE)
                            .set(CHECKSUM_COLUMN, checkSum)
                            .execute(store);
                } else if (!expectedCheckSum.equals(checkSum)) {
                    throw new DatabaseException(
                            String.format("CheckSum check failed for change set '%s'. Should be '%s' but was '%s'",
                            this, expectedCheckSum, checkSum));
                }
            } else {
                for (GAEChange change : changes) {
                    change.execute(store);
                }
                new GAEInsert(LOG_TABLE)
                        .set(TITLE_COLUMN, title)
                        .set(LOCATION_COLUMN, location)
                        .set(AUTHOR_COLUMN, author)
                        .set(COMMENT_COLUMN, comment)
                        .set(CHECKSUM_COLUMN, checkSum)
                        .set(EXECUTED_AT_COLUMN, new Date())
                        .execute(store);
            }
        }
    }

    private boolean supports(final Collection<String> context) {
        return this.context == null || this.context.isEmpty() || this.context.containsAll(context);
    }

    @Override
    public String toString() {
        final DescriptionBuilder builder = new DescriptionBuilder(this);
        builder.append(title).append(location).append(author);
        if (context != null && !context.isEmpty()) {
            builder.append("context", context);
        }
        if (!StringUtils.isEmpty(comment)) {
            builder.append("comment", comment);
        }
        if (!changes.isEmpty()) {
            builder.append("changes", changes);
        }
        return builder.toString();
    }
}
