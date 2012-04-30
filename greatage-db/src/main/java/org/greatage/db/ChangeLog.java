package org.greatage.db;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ChangeLog {

    ChangeLog name(String name);

    ChangeSet begin(String title);

    ChangeLog end();

    ChangeLog dropAll();

    Insert insert(String entityName);

    Update update(String entityName);

    Delete delete(String entityName);

    Select select(String entityName);

    ConditionEntry condition(String propertyName);

    interface ChangeSet {

        ChangeSet author(String author);

        ChangeSet comment(String comment);

        ChangeSet context(String... context);
    }

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

        Select keys();

        Select where(Condition condition);
    }

    interface Condition {

        Condition and(Condition condition);

        Condition or(Condition condition);
    }

    interface ConditionEntry {

        Condition gt(Object value);

        Condition ge(Object value);

        Condition lt(Object value);

        Condition le(Object value);

        Condition eq(Object value);

        Condition ne(Object value);

        Condition in(Object... values);
    }
}
