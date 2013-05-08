package org.greatage.domain

import org.example.model.Account
import spock.lang.Shared
import spock.lang.Specification

/**
 * +-------------------------------+
 * |            ACCOUNT            |
 * +----+----------+---------------+
 * | ID |   NAME   | REGISTERED_AT |
 * +----+----------+---------------+
 * | 1  | account1 |    2010-10-10 |
 * | 2  | account2 |          null |
 * | 3  | account3 |    2012-12-12 |
 * +----+----------+---------------+
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
abstract class RepositorySpecification extends Specification {

    @Shared
    protected Repository repository

    def "should read entity state by its primary key"() {
        when:
        def actual = repository.read(Account.class, id)
        then:
        actual != null
        matchesAccount(actual, id, name, registeredAt)

        when:
        actual.name = null
        actual.registeredAt = null
        def another = repository.read(actual)
        then:
        another != null
        matchesAccount(another, id, name, registeredAt)

        where:
        id | name       | registeredAt
        1l | "account1" | date("2010-10-10")
        2l | "account2" | null
        3l | "account3" | date("2012-12-12")
    }

    def "should read null if no entity state is found for specified primary key"() {
        when:
        def actual = repository.read(Account.class, id)
        then:
        actual == null

        where:
        id << [10l, -1l, 10l]
    }

    def "should throw exception if specified primary key is null"() {
        when:
        repository.read(Account.class, (Long) null)
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException
    }

    def "should read all entities states by their primary keys"() {
        when:
        Map<Long, Account> actual = repository.readAll(Account.class, 1l, 2l, 3l);
        then:
        actual != null && actual.size() == 3
        matchesAccount(actual[1l], 1l, "account1", date("2010-10-10"))
        matchesAccount(actual[2l], 2l, "account2", null)
        matchesAccount(actual[3l], 3l, "account3", date("2012-12-12"))

        when:
        actual = repository.readAll(Account.class, [1l, 2l, 3l]);
        then:
        actual != null && actual.size() == 3
        matchesAccount(actual[1l], 1l, "account1", date("2010-10-10"))
        matchesAccount(actual[2l], 2l, "account2", null)
        matchesAccount(actual[3l], 3l, "account3", date("2012-12-12"))
    }

    def "should read null if no entity state is found for one of specified primary keys"() {
        when:
        Map<Long, Account> actual = repository.readAll(Account.class, 1l, 20l, 3l);
        then:
        actual != null && actual.size() == 3
        matchesAccount(actual[1l], 1l, "account1", date("2010-10-10"))
        actual[2l] == null
        matchesAccount(actual[3l], 3l, "account3", date("2012-12-12"))

        when:
        actual = repository.readAll(Account.class, [1l, 20l, 3l]);
        then:
        actual != null && actual.size() == 3
        matchesAccount(actual[1l], 1l, "account1", date("2010-10-10"))
        actual[2l] == null
        matchesAccount(actual[3l], 3l, "account3", date("2012-12-12"))
    }

    def "should read all nulls if no entity state is found for all of specified primary keys"() {
        when:
        Map<Long, Account> actual = repository.readAll(Account.class, 10l, 20l, 30l);
        then:
        actual != null && actual.size() == 3
        actual[1l] == null
        actual[2l] == null
        actual[3l] == null

        when:
        actual = repository.readAll(Account.class, [10l, 20l, 30l]);
        then:
        actual != null && actual.size() == 3
        actual[1l] == null
        actual[2l] == null
        actual[3l] == null
    }

    def "should throw exception if one of specified primary keys is null"() {
        when:
        repository.readAll(Account.class, 10l, null, 30l);
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException

        when:
        repository.readAll(Account.class, [10l, null, 30l]);
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException
    }

    def "should throw exception if specified primary keys is null"() {
        when:
        repository.readAll(Account.class, (Long[]) null);
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException

        when:
        repository.readAll(Account.class, (Collection<Long>) null);
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException
    }

    protected boolean matchesAccount(final Account account,
                                     final Long id, final String name, final Date registeredAt) {
        return account.id == id &&
                account.name == name &&
                account.registeredAt == registeredAt;
    }

    protected Date date(final String input) {
        return Date.parse("yyyy-MM-dd", input)
    }
}
