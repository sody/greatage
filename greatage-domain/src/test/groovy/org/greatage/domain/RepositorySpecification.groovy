package org.greatage.domain

import org.example.model.Account
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
abstract class RepositorySpecification extends Specification {

    @Shared
    protected Repository repository

    def "should read state for single entity by its primary key"() {
        when:
        def actual = repository.read(Account.class, id)
        then:
        actual != null
        matchesAccount(actual, id, name, registeredAt)

        where:
        id | name                 | registeredAt
        1l | "account_for_read_1" | date("2010-10-10")
        2l | "account_for_read_2" | null
        3l | "account_for_read_3" | date("2012-12-12")
    }

    def "should read state for single entity"() {
        given:
        def account = account(id, "read_test", date("2013-01-01"))

        when:
        def actual = repository.read(account)
        then:
        actual != null
        matchesAccount(actual, id, name, registeredAt)

        where:
        id | name                 | registeredAt
        1l | "account_for_read_1" | date("2010-10-10")
        2l | "account_for_read_2" | null
        3l | "account_for_read_3" | date("2012-12-12")
    }

    def "should read null if no state is found for specified primary key"() {
        when:
        def actual = repository.read(Account.class, id)
        then:
        actual == null

        when:
        def account = account(id, "read_test", null)
        actual = repository.read(account)
        then:
        actual == null

        where:
        id << [100l, -1l, 70l]
    }

    def "should read null if no state is found for specified entity"() {
        given:
        def account = account(id, "read_test", null)

        when:
        def actual = repository.read(account)
        then:
        actual == null

        where:
        id << [100l, -1l, 70l]
    }

    def "should read all states for multiple entities by their primary keys"() {
        when:
        Map<Long, Account> actual = repository.readAll(Account.class, 1l, 2l, 3l)
        then:
        actual != null && actual.size() == 3
        matchesAccount(actual[1l], 1l, "account_for_read_1", date("2010-10-10"))
        matchesAccount(actual[2l], 2l, "account_for_read_2", null)
        matchesAccount(actual[3l], 3l, "account_for_read_3", date("2012-12-12"))

        when:
        actual = repository.readAll(Account.class, [1l, 2l, 3l])
        then:
        actual != null && actual.size() == 3
        matchesAccount(actual[1l], 1l, "account_for_read_1", date("2010-10-10"))
        matchesAccount(actual[2l], 2l, "account_for_read_2", null)
        matchesAccount(actual[3l], 3l, "account_for_read_3", date("2012-12-12"))
    }

    def "should read all states for multiple entities"() {
        given:
        def account1 = account(1l, "read_test_1", date("2013-01-01"))
        def account2 = account(2l, "read_test_2", date("2013-01-02"))
        def account3 = account(3l, "read_test_3", date("2013-01-03"))

        when:
        Map<Long, Account> actual = repository.readAll(account1, account2, account3)
        then:
        actual != null && actual.size() == 3
        matchesAccount(actual[1l], 1l, "account_for_read_1", date("2010-10-10"))
        matchesAccount(actual[2l], 2l, "account_for_read_2", null)
        matchesAccount(actual[3l], 3l, "account_for_read_3", date("2012-12-12"))

        when:
        actual = repository.readAll([account1, account2, account3])
        then:
        actual != null && actual.size() == 3
        matchesAccount(actual[1l], 1l, "account_for_read_1", date("2010-10-10"))
        matchesAccount(actual[2l], 2l, "account_for_read_2", null)
        matchesAccount(actual[3l], 3l, "account_for_read_3", date("2012-12-12"))
    }

    def "should read null if no state is found for one of specified primary keys"() {
        when:
        Map<Long, Account> actual = repository.readAll(Account.class, 1l, 100l, 3l)
        then:
        actual != null && actual.size() == 3
        matchesAccount(actual[1l], 1l, "account_for_read_1", date("2010-10-10"))
        actual[2l] == null
        matchesAccount(actual[3l], 3l, "account_for_read_3", date("2012-12-12"))

        when:
        actual = repository.readAll(Account.class, [1l, 20l, 3l])
        then:
        actual != null && actual.size() == 3
        matchesAccount(actual[1l], 1l, "account_for_read_1", date("2010-10-10"))
        actual[2l] == null
        matchesAccount(actual[3l], 3l, "account_for_read_3", date("2012-12-12"))
    }

    def "should read null if no state is found for one of specified entities"() {
        given:
        def account1 = account(1l, "read_test", null)
        def account2 = account(2l, "read_test", null)
        def none = account(100l, "read_test", null)

        when:
        Map<Long, Account> actual = repository.readAll(account1, none, account2)
        then:
        actual != null && actual.size() == 3
        matchesAccount(actual[1l], 1l, "account_for_read_1", date("2010-10-10"))
        matchesAccount(actual[2l], 2l, "account_for_read_2", null)
        actual[100l] == null

        when:
        actual = repository.readAll([account1, account2, none])
        then:
        actual != null && actual.size() == 3
        matchesAccount(actual[1l], 1l, "account_for_read_1", date("2010-10-10"))
        matchesAccount(actual[2l], 2l, "account_for_read_2", null)
        actual[100l] == null
    }

    def "should throw exception for read with null parameters"() {
        given:
        def account1 = account(1l, "read_test", null)
        def account2 = account(2l, "read_test", null)

        when:
        repository.read(Account.class, null)
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException

        when:
        repository.read(null)
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException

        when:
        repository.readAll(Account.class, (Long[]) null)
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException

        when:
        repository.readAll(Account.class, (Collection<Long>) null)
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException

        when:
        repository.readAll((Account[]) null)
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException

        when:
        repository.readAll((Collection<Account>) null)
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException

        when:
        repository.readAll(Account.class, 1l, null, 3l)
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException

        when:
        repository.readAll(Account.class, [1l, null, 3l])
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException

        when:
        repository.readAll(account1, account2, null)
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException

        when:
        repository.readAll([account1, account2, null])
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException
    }

    def "should create new state for single entity"() {
        given:
        def account = account(30l, "create_test", date("2013-01-11"))

        when:
        repository.create(account)
        def actual = repository.read(Account.class, 30l)
        then:
        actual != null
        matchesAccount(actual, 30l, "create_test", date("2013-01-11"))
    }

//    def "should throw exception for create if specified entity already exist"() {
//        given:
//        def account = account(1l, "create_test", date("2013-01-11"))
//
//        when:
//        repository.create(account)
//        then:
//        thrown(RuntimeException.class)
//    }

    def "should create all new states for multiple entities"() {
        given:
        def account1 = account(31l, "create_test_1", date("2013-01-01"))
        def account2 = account(32l, "create_test_2", date("2013-01-02"))
        def account3 = account(33l, "create_test_3", date("2013-01-03"))
        def account4 = account(34l, "create_test_4", date("2013-01-04"))

        when:
        repository.createAll(account1, account2)
        def actual = repository.read(Account.class, 31l)
        def actual2 = repository.read(Account.class, 32l)
        then:
        actual != null && actual2 != null
        matchesAccount(actual, 31l, "create_test_1", date("2013-01-01"))
        matchesAccount(actual2, 32l, "create_test_2", date("2013-01-02"))

        when:
        repository.createAll([account3, account4])
        def actual3 = repository.read(Account.class, 33l)
        def actual4 = repository.read(Account.class, 34l)
        then:
        actual3 != null && actual4 != null
        matchesAccount(actual3, 33l, "create_test_3", date("2013-01-03"))
        matchesAccount(actual4, 34l, "create_test_4", date("2013-01-04"))
    }

    def "should throw exception for create all if one of specified entities already exist"() {
        given:
        def account1 = account(1l, "create_test_1", date("2013-01-11"))
        def account2 = account(2l, "create_test_2", date("2013-01-11"))
        def account3 = account(35l, "create_test_3", date("2013-01-11"))
        def account4 = account(36l, "create_test_4", date("2013-01-11"))

        when: //todo: move to separate test
        repository.create(account1)
        then:
        thrown(RuntimeException.class)

        when:
        repository.createAll(account1, account3)
        then:
        thrown(RuntimeException.class)

        when:
        repository.createAll([account2, account4])
        then:
        thrown(RuntimeException.class)
    }

    def "should throw exception for create with null parameters"() {
        given:
        def account1 = account(41l, "create_test", null)
        def account2 = account(42l, "create_test", null)

        when:
        repository.create(null)
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException

        when:
        repository.createAll((Account[]) null)
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException

        when:
        repository.createAll((Collection<Account>) null)
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException

        when:
        repository.createAll(account1, account2, null)
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException

        when:
        repository.createAll([account1, account2, null])
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException
    }

    def "should update state for single entity"() {
        given:
        def account = account(10l, "update_test", date("2013-01-01"))

        when:
        repository.update(account)
        def actual = repository.read(Account.class, 10l)
        then:
        actual != null
        matchesAccount(actual, 10l, "update_test", date("2013-01-01"))
    }

//    def "should throw exception for update if specified entity doesn't exist"() {
//        given:
//        def account = account(100l, "create_test", date("2013-01-11"))
//
//        when:
//        repository.update(account)
//        then:
//        thrown(RuntimeException.class)
//    }

    def "should update all states for multiple entities"() {
        given:
        def account1 = account(11l, "update_test_1", date("2013-02-01"))
        def account2 = account(12l, "update_test_2", date("2013-02-02"))
        def account3 = account(13l, "update_test_3", date("2013-02-03"))
        def account4 = account(14l, "update_test_4", date("2013-02-04"))

        when:
        repository.updateAll(account1, account2)
        def actual1 = repository.read(Account.class, 11l)
        def actual2 = repository.read(Account.class, 12l)
        then:
        actual1 != null && actual2 != null
        matchesAccount(actual1, 11l, "update_test_1", date("2013-02-01"))
        matchesAccount(actual2, 12l, "update_test_2", date("2013-02-02"))

        when:
        repository.updateAll([account3, account4])
        def actual3 = repository.read(Account.class, 13l)
        def actual4 = repository.read(Account.class, 14l)
        then:
        actual3 != null && actual4 != null
        matchesAccount(actual3, 13l, "update_test_3", date("2013-02-03"))
        matchesAccount(actual4, 14l, "update_test_4", date("2013-02-04"))
    }

    def "should throw exception for update all if one of specified entities doesn't exist"() {
        given:
        def account1 = account(15l, "update_test_1", date("2013-01-01"))
        def account2 = account(16l, "update_test_2", date("2013-01-02"))
        def none = account(100l, "update_test_3", date("2013-01-03"))

        when://todo: move to separate test
        repository.update(none)
        then:
        thrown(RuntimeException.class)

        when:
        repository.updateAll(account1, none)
        then:
        thrown(RuntimeException.class)

        when:
        repository.updateAll([account2, none])
        then:
        thrown(RuntimeException.class)
    }

    def "should throw exception for update with null parameters"() {
        given:
        def account1 = account(11l, "update_test", null)
        def account2 = account(12l, "update_test", null)

        when:
        repository.update(null)
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException

        when:
        repository.updateAll((Account[]) null)
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException

        when:
        repository.updateAll((Collection<Account>) null)
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException

        when:
        repository.updateAll(account1, account2, null)
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException

        when:
        repository.updateAll([account1, account2, null])
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException
    }

    def "should delete state for single entity by its primary key"() {
        when:
        repository.delete(Account.class, 20l)
        def actual = repository.read(Account.class, 20l)
        then:
        actual == null
    }

    def "should delete state for single entity"() {
        given:
        def account = account(25l, "delete_test", date("2013-01-01"))

        when:
        repository.delete(account)
        def actual = repository.read(Account.class, 25l)
        then:
        actual == null
    }

    def "should throw exception for delete if entity with specified primary key doesn't exist"() {
        when:
        repository.delete(Account.class, id)
        then:
        thrown(RuntimeException.class)

        where:
        id << [100l, -1l, 70l]
    }

//    def "should throw exception for delete if specified entity doesn't exist"() {
//        given:
//        def account = account(id, "delete_test", date("2013-01-11"))
//
//        when:
//        repository.delete(account)
//        then:
//        thrown(RuntimeException.class)
//
//        where:
//        id << [100l, -1l, 70l]
//    }

    def "should delete all states for multiple entities by their primary keys"() {
        when:
        repository.deleteAll(Account.class, 21l, 22l)
        def actual1 = repository.read(Account.class, 21l)
        def actual2 = repository.read(Account.class, 22l)
        then:
        actual1 == null
        actual2 == null

        when:
        repository.deleteAll(Account.class, [23l, 24l])
        def actual3 = repository.read(Account.class, 23l)
        def actual4 = repository.read(Account.class, 24l)
        then:
        actual3 == null
        actual4 == null
    }

    def "should delete all states for multiple entities"() {
        given:
        def account1 = account(26l, "delete_test_1", date("2013-02-01"))
        def account2 = account(27l, "delete_test_2", date("2013-02-02"))
        def account3 = account(28l, "delete_test_3", date("2013-02-03"))
        def account4 = account(29l, "delete_test_4", date("2013-02-04"))

        when:
        repository.deleteAll(account1, account2)
        def actual1 = repository.read(Account.class, 26l)
        def actual2 = repository.read(Account.class, 27l)
        then:
        actual1 == null
        actual2 == null

        when:
        repository.deleteAll([account3, account4])
        def actual3 = repository.read(Account.class, 28l)
        def actual4 = repository.read(Account.class, 29l)
        then:
        actual3 == null
        actual4 == null
    }

    def "should throw exception for delete all if one of entities with specified primary keys doesn't exist"() {
        when://todo: move to separate test
        repository.delete(Account.class, 100l)
        then:
        thrown(RuntimeException.class)

        when:
        repository.deleteAll(Account.class, 30l, 100l)
        then:
        thrown(RuntimeException.class)

        when:
        repository.deleteAll(Account.class, [31l, 100l])
        then:
        thrown(RuntimeException.class)
    }

    @Ignore("not working yet")
    def "should throw exception for delete all if one of specified entities doesn't exist"() {
        given:
        def account1 = account(32l, "delete_test_1", date("2013-01-01"))
        def account2 = account(33l, "delete_test_2", date("2013-01-02"))
        def none = account(100l, "delete_test_3", date("2013-01-03"))

        when://todo: move to separate test
        repository.delete(none)
        then:
        thrown(RuntimeException.class)

        when:
        repository.deleteAll(account1, none)
        then:
        thrown(RuntimeException.class)

        when:
        repository.deleteAll([account2, none])
        then:
        thrown(RuntimeException.class)
    }

    def "should throw exception for delete with null parameters"() {
        given:
        def account1 = account(34l, "delete_test", null)
        def account2 = account(35l, "delete_test", null)

        when:
        repository.delete(Account.class, null)
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException

        when:
        repository.delete(null)
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException

        when:
        repository.deleteAll(Account.class, (Long[]) null)
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException

        when:
        repository.deleteAll(Account.class, (Collection<Long>) null)
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException

        when:
        repository.deleteAll((Account[]) null)
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException

        when:
        repository.deleteAll((Collection<Account>) null)
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException

        when:
        repository.deleteAll(Account.class, 1l, null, 3l)
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException

        when:
        repository.deleteAll(Account.class, [1l, null, 3l])
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException

        when:
        repository.deleteAll(account1, account2, null)
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException

        when:
        repository.deleteAll([account1, account2, null])
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException
    }

    protected abstract Account createAccount(long id);

    protected Account account(final long id, final String name, final Date registeredAt) {
        final Account account = createAccount(id)
        account.name = name
        account.registeredAt = registeredAt
        return account
    }

    protected boolean matchesAccount(final Account account,
                                     final Long id, final String name, final Date registeredAt) {
        return account.id == id &&
                account.name == name &&
                account.registeredAt == registeredAt
    }

    protected Date date(final String input) {
        return Date.parse("yyyy-MM-dd", input)
    }
}
