package org.greatage.db.gae;

import com.google.appengine.api.datastore.DatastoreService;
import org.greatage.db.ChangeSet;
import org.greatage.db.CheckSumUtils;
import org.greatage.util.DescriptionBuilder;
import org.greatage.util.StringUtils;

import java.util.*;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAEChangeSet implements ChangeSet, DataStoreCallback {
    private final String title;
    private String author = "<unknown>";
    private String location = "<unknown>";

    private final Set<String> context = new HashSet<String>();
    private String comment;
    private String checkSum;

    private final List<GAEChange> changes = new ArrayList<GAEChange>();

    GAEChangeSet(final String title) {
        this.title = title;
    }

    public ChangeSet author(final String author) {
        this.author = author;
        return this;
    }

    public ChangeSet location(final String location) {
        this.location = location;
        return this;
    }

    public ChangeSet comment(final String comment) {
        this.comment = comment;
        return this;
    }

    public ChangeSet context(final String... context) {
        Collections.addAll(this.context, context);
        return this;
    }

    public ChangeSet.Insert insert(final String entityName) {
        return addChange(new GAEInsert(entityName));
    }

    public ChangeSet.Update update(final String entityName) {
        return addChange(new GAEUpdate(entityName));
    }

    public ChangeSet.Delete delete(final String entityName) {
        return addChange(new GAEDelete(entityName));
    }

    public ChangeSet.Select select(final String entityName) {
        return new GAESelect(entityName);
    }

    public ChangeSet.ConditionEntry condition(final String propertyName) {
        return new GAEConditionEntry(propertyName);
    }

    public void doInDataStore(final DatastoreService dataStore) {
        for (GAEChange change : changes) {
            change.doInDataStore(dataStore);
        }
    }

    String getTitle() {
        return title;
    }

    String getAuthor() {
        return author;
    }

    String getLocation() {
        return location;
    }

    String getComment() {
        return comment;
    }

    String getCheckSum() {
        if (checkSum == null) {
            checkSum = CheckSumUtils.compositeCheckSum(toString());
        }
        return checkSum;
    }

    boolean supports(final Set<String> runContext) {
        return context.isEmpty() || context.containsAll(runContext);
    }

    private <T extends GAEChange> T addChange(final T change) {
        changes.add(change);
        return change;
    }

    @Override
    public String toString() {
        final DescriptionBuilder builder = new DescriptionBuilder(getClass());
        builder.append(title).append(author).append(location);
        if (!context.isEmpty()) {
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
