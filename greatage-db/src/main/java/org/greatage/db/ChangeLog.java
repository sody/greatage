package org.greatage.db;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ChangeLog {

    ChangeLog name(String name);


    ChangeSet begin(String title);

    ChangeLog end();


    ChangeLog statement(String statement);

    ChangeLog dropAll();

    Insert insert(String table);

    Update update(String table);

    Delete delete(String table);


    Select select(String table);

    ConditionEntry condition(String property);


    interface ChangeSet {

        ChangeSet author(String author);

        ChangeSet comment(String comment);

        ChangeSet context(String... context);
    }

    interface Insert {

        Insert set(String property, Object value);

        Insert set(String property, Select select);

        Insert into(String... properties);

        Insert values(Object... values);
    }

    interface Update {

        Update set(String property, Object value);

        Update set(String property, Select select);

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
