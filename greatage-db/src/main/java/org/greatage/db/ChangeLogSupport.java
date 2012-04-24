package org.greatage.db;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class ChangeLogSupport {
    private static final String DEFAULT_AUTHOR = "<unknown>";

    private ChangeLog database;
    private ChangeSet changeSet;

    private String author;
    private String location;

    protected ChangeLogSupport() {
        location = getClass().getName();
        author = DEFAULT_AUTHOR;
    }

    public final void execute(final ChangeLog database) {
        this.database = database;
        init();
        this.database = null;
    }

    protected void location(final String location) {
        assert location != null;

        this.location = location;
    }

    protected void author(final String author) {
        assert author != null;

        this.author = author;
    }

    protected ChangeSet begin(final String title) {
        assert database != null;
        assert title != null;

        changeSet = database.changeSet(title).author(author).location(location);
        return changeSet;
    }

    protected ChangeSet.Insert insert(final String entityName) {
        return changeSet.insert(entityName);
    }

    protected ChangeSet.Update update(final String entityName) {
        return changeSet.update(entityName);
    }

    protected ChangeSet.Delete delete(final String entityName) {
        return changeSet.delete(entityName);
    }

    protected ChangeSet.Select select(final String entityName) {
        return changeSet.select(entityName);
    }

    protected ChangeSet.ConditionEntry condition(final String propertyName) {
        return changeSet.condition(propertyName);
    }

    protected void add(final ChangeLogSupport changeLog) {
        assert database != null;
        assert changeLog != null;

        changeLog.execute(database);
    }

    protected abstract void init();
}
