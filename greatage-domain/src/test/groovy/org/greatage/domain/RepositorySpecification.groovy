package org.greatage.domain

import org.example.model.Company
import spock.lang.Shared
import spock.lang.Specification

/**
 * +-------------------------------+--------------+-------------------------------------------------+
 * |            COMPANY            | COMPANY_INFO |                     ADDRESS                     |
 * +----+----------+---------------+--------------+-------------+-------------+--------+------------+
 * | ID |   NAME   | REGISTERED_AT |   INFO_CODE  |   ADDRESS1  |   ADDRESS2  | CITY   | COUNTRY_ID |
 * +----+----------+---------------+--------------+-------------+-------------+--------+------------+
 * | 1  | company1 |          null |         null |        null |        null |   null |       null |
 * | 2  | company2 |    2010-10-10 |           C2 | address_2_1 | address_2_2 | city_2 |          1 |
 * | 3  | company3 |          null |           C3 | address_3_1 | address_3_2 |   null |       null |
 * | 4  |     null |          null |           C4 |        null |        null |   null |          3 |
 * | 5  |  company |    2001-01-01 |         null |        null |        null |   null |       null |
 * | 6  |  company |    2001-02-02 |         null |        null |        null |   null |       null |
 * +----+----------+---------------+--------------+-------------+-------------+--------+------------+
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
abstract class RepositorySpecification extends Specification {

    @Shared
    protected Repository repository

    def "should read single entity data by its primary key"() {
        when:
        def actual = repository.read(Company.class, id)
        then:
        matchesCompany(actual, id, name, registeredAt)

        where:
        id | name       | registeredAt
        1l | "company1" | null
        2l | "company2" | date("2010-10-10")
        3l | "company3" | null
    }

    def "should read null if no entity data is found for specified primary key"() {
        when:
        def actual = repository.read(Company.class, id)
        then:
        actual == null

        where:
        id << [10l, -1l, 10l]
    }

    def "should throw exception if specified primary key is null"() {
        when:
        repository.read(Company.class, (Long) null)
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException
    }

    def "should read all entities data by their primary keys"() {
        when:
        Map<Long, Company> actual = repository.readAll(Company.class, 1l, 2l, 3l);
        then:
        actual != null && actual.size() == 3
        matchesCompany(actual[1l], 1l, "company1", null)
        matchesCompany(actual[2l], 2l, "company2", date("2010-10-10"))
        matchesCompany(actual[3l], 3l, "company3", null)

        when:
        actual = repository.readAll(Company.class, [1l, 2l, 3l]);
        then:
        actual != null && actual.size() == 3
        matchesCompany(actual[1l], 1l, "company1", null)
        matchesCompany(actual[2l], 2l, "company2", date("2010-10-10"))
        matchesCompany(actual[3l], 3l, "company3", null)
    }

    def "should read null if no entity data is found for one of specified primary keys"() {
        when:
        Map<Long, Company> actual = repository.readAll(Company.class, 1l, 20l, 3l);
        then:
        actual != null && actual.size() == 3
        matchesCompany(actual[1l], 1l, "company1", null)
        actual[2l] == null
        matchesCompany(actual[3l], 3l, "company3", null)

        when:
        actual = repository.readAll(Company.class, [1l, 20l, 3l]);
        then:
        actual != null && actual.size() == 3
        matchesCompany(actual[1l], 1l, "company1", null)
        actual[2l] == null
        matchesCompany(actual[3l], 3l, "company3", null)
    }

    def "should read all nulls if no entity data is found for all of specified primary keys"() {
        when:
        Map<Long, Company> actual = repository.readAll(Company.class, 10l, 20l, 30l);
        then:
        actual != null && actual.size() == 3
        actual[1l] == null
        actual[2l] == null
        actual[3l] == null

        when:
        actual = repository.readAll(Company.class, [10l, 20l, 30l]);
        then:
        actual != null && actual.size() == 3
        actual[1l] == null
        actual[2l] == null
        actual[3l] == null
    }

    def "should throw exception if one of specified primary keys is null"() {
        when:
        repository.readAll(Company.class, 10l, null, 30l);
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException

        when:
        repository.readAll(Company.class, [10l, null, 30l]);
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException
    }

    def "should throw exception if specified primary keys is null"() {
        when:
        repository.readAll(Company.class, (Long[]) null);
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException

        when:
        repository.readAll(Company.class, (Collection<Long>) null);
        then:
        thrown(RuntimeException.class) //todo: should be NullPointerException
    }

    protected boolean matchesCompany(final Company company,
                                     final Long id, final String name, final Date registeredAt) {
        return company.id == id &&
                company.name == name &&
                company.registeredAt == registeredAt;
    }

    protected Date date(final String input) {
        return Date.parse("yyyy-MM-dd", input)
    }
}
