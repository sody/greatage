package org.greatage.db.gae;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import org.greatage.db.ChangeLog;
import org.greatage.db.DatabaseException;

import java.util.Collection;
import java.util.Date;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAEChangeLog implements ChangeLog, GAEConstants {
    private final DatastoreService store;
    private final boolean clearCheckSums;
    private final Collection<String> context;

    private String name = "<unknown>";
    private String author = "<unknown>";

    private GAEChangeSet changeSet;

    GAEChangeLog(final DatastoreService store, final Collection<String> context, final boolean clearCheckSums) {
        this.store = store;
        this.clearCheckSums = clearCheckSums;
        this.context = context;
    }

    public ChangeLog name(final String name) {
        this.name = name;
        return this;
    }

    public ChangeSet begin(final String title) {
        end();
        changeSet = new GAEChangeSet(title, name, author);
        return changeSet;
    }

    public ChangeLog end() {
        if (changeSet != null) {
            changeSet.execute(store, context, clearCheckSums);
            changeSet = null;
        }
        return this;
    }

    public ChangeLog dropAll() {
        addChange(new GAEDeleteAll());
        return this;
    }

    public ChangeLog.Insert insert(final String entityName) {
        return addChange(new GAEInsert(entityName));
    }

    public ChangeLog.Update update(final String entityName) {
        return addChange(new GAEUpdate(entityName));
    }

    public ChangeLog.Delete delete(final String entityName) {
        return addChange(new GAEDelete(entityName));
    }

    public ChangeLog.Select select(final String entityName) {
        return new GAESelect(entityName);
    }

    public ChangeLog.ConditionEntry condition(final String propertyName) {
        return new GAEConditionEntry(propertyName);
    }

    private <T extends GAEChange> T addChange(final T change) {
        changeSet.addChange(change);
        return change;
    }
}
