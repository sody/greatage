package org.greatage.db;

/**
 * @author Ivan Khalopik
 */
public interface ChangeSet {

    ChangeSet author(String author);

    ChangeSet location(String location);

    ChangeSet comment(String comment);

    ChangeSet context(String... context);

    Insert insert(String entityName);

    Update update(String entityName);

    Delete delete(String entityName);

    Select select(String entityName);

    ConditionEntry condition(String propertyName);

    interface Insert {

        Insert set(String propertyName, Object value);

        Insert set(String propertyName, Select select);

        Insert into(String... propertyNames);

        Insert values(Object... values);
    }

    interface Update {

        Update set(String propertyName, Object value);

        Update set(String propertyName, Select select);

        Update where(Condition condition);
    }

    interface Delete {

        Delete where(Condition condition);
    }

    interface Select {

        Select unique();

        Select where(Condition condition);
    }

    interface Condition {

        Condition and(Condition condition);

        Condition or(Condition condition);
    }

    interface ConditionEntry {

        Condition greaterThan(Object value);

        Condition greaterOrEqual(Object value);

        Condition lessThan(Object value);

        Condition lessOrEqual(Object value);

        Condition equal(Object value);

        Condition notEqual(Object value);

        Condition in(Object... values);
    }
}
