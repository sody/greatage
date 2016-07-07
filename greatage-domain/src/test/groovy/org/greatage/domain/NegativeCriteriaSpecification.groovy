package org.greatage.domain

import org.example.criteria.$
import org.example.model.Company
import spock.lang.Shared
import spock.lang.Specification

import static org.example.criteria.$.company$

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
abstract class NegativeCriteriaSpecification extends Specification {

    @Shared
    protected Repository repository

    def "negative equal criteria should filter entities to those that have property value not equal to specified and not null"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                             | expected
        $.not(company$.id$.eq(1l))                           | [2, 3, 4, 5, 6]
        $.not(company$.name$.equal("company"))               | [1, 2, 3]
        $.not(company$.registeredAt$.eq(date("2001-01-02"))) | [2, 5, 6]
    }

    def "negative equal criteria with null parameter should filter entities to those that have property value not equal to null"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                  | expected
        $.not(company$.id$.equal(null))           | [1, 2, 3, 4, 5, 6]
        $.not(company$.name$.eq(null))            | [1, 2, 3, 5, 6]
        $.not(company$.registeredAt$.equal(null)) | [2, 5, 6]
    }

    def "negative not equal criteria should filter entities to those that have property value equal to specified"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                             | expected
        $.not(company$.id$.ne(1l))                           | [1]
        $.not(company$.name$.notEqual("company"))            | [5, 6]
        $.not(company$.registeredAt$.ne(date("2001-01-01"))) | [5]
    }

    def "negative not equal criteria with null parameter should filter entities to those that have null property value"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                               | expected
        $.not(company$.id$.notEqual(null))     | []
        $.not(company$.name$.ne(null))         | [4]
        $.not(company$.registeredAt$.ne(null)) | [1, 3, 4]
    }

    def "negative is null criteria should filter entities to those that have property value not equal to null"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                               | expected
        $.not(company$.id$.isNull())           | [1, 2, 3, 4, 5, 6]
        $.not(company$.name$.isNull())         | [1, 2, 3, 5, 6]
        $.not(company$.registeredAt$.isNull()) | [2, 5, 6]
    }

    def "negative is not null criteria should filter entities to those that have property value equal to null"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                | expected
        $.not(company$.id$.notNull())           | []
        $.not(company$.name$.notNull())         | [4]
        $.not(company$.registeredAt$.notNull()) | [1, 3, 4]
    }

    def "negative greater than criteria should filter entities to those that have property value less or equal to specified and not null"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                             | expected
        $.not(company$.id$.greaterThan(2l))                  | [1, 2]
        $.not(company$.name$.greaterThan("z"))               | [1, 2, 3, 5, 6]
        $.not(company$.registeredAt$.gt(date("2001-01-01"))) | [5]
    }

    def "negative greater than criteria with null parameter should filter all entities to empty result"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                               | expected
        $.not(company$.id$.gt(null))           | []
        $.not(company$.name$.gt(null))         | []
        $.not(company$.registeredAt$.gt(null)) | []
    }

    def "negative greater or equal criteria should filter entities to those that have property value less than specified and not null"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                             | expected
        $.not(company$.id$.ge(4l))                           | [1, 2, 3]
        $.not(company$.name$.ge("z"))                        | [1, 2, 3, 5, 6]
        $.not(company$.registeredAt$.ge(date("2001-02-02"))) | [5]
    }

    def "negative greater or equal criteria with null parameter should filter all entities to empty result"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                               | expected
        $.not(company$.id$.ge(null))           | []
        $.not(company$.name$.ge(null))         | []
        $.not(company$.registeredAt$.ge(null)) | []
    }

    def "negative less than criteria should filter entities to those that have property value greater or equal to specified and not null"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                             | expected
        $.not(company$.id$.lt(4l))                           | [4, 5, 6]
        $.not(company$.name$.lt("a"))                        | [1, 2, 3, 5, 6]
        $.not(company$.registeredAt$.lt(date("2001-02-02"))) | [2, 6]
    }

    def "negative less than criteria with null parameter should filter all entities to empty result"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                               | expected
        $.not(company$.id$.lt(null))           | []
        $.not(company$.name$.lt(null))         | []
        $.not(company$.registeredAt$.lt(null)) | []
    }

    def "negative less or equal criteria should filter entities to those that have property value greater than specified and not null"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                             | expected
        $.not(company$.id$.le(1l))                           | [2, 3, 4, 5, 6]
        $.not(company$.name$.le("a"))                        | [1, 2, 3, 5, 6]
        $.not(company$.registeredAt$.le(date("2001-01-01"))) | [2, 6]
    }

    def "negative less or equal with null parameter criteria should filter all entities to empty result"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                               | expected
        $.not(company$.id$.le(null))           | []
        $.not(company$.name$.le(null))         | []
        $.not(company$.registeredAt$.le(null)) | []
    }

    def "negative in criteria should filter entities to those that have property value not in specified set and not null"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                                                 | expected
        $.not(company$.id$.in(1l, 2l, 3l))                                       | [4, 5, 6]
        $.not(company$.name$.in("company", "company1"))                          | [2, 3]
        $.not(company$.registeredAt$.in(date("2001-01-01"), date("2001-02-02"))) | [2]
    }

    def "negative in criteria with empty parameter should not filter entities"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                             | expected
        $.not(company$.id$.in([]))           | [1, 2, 3, 4, 5, 6]
        $.not(company$.name$.in([]))         | [1, 2, 3, 4, 5, 6]
        $.not(company$.registeredAt$.in([])) | [1, 2, 3, 4, 5, 6]
    }

    def "negative in criteria with null parameters should filter entities to those that have property value not in specified set and not null"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                                   | expected
        $.not(company$.id$.in([1l, null]))                         | [2, 3, 4, 5, 6]
        $.not(company$.name$.in("company2", "company8", null))     | [1, 3, 5, 6]
        $.not(company$.registeredAt$.in(date("2001-01-01"), null)) | [2, 6]
    }

    def "negative not in criteria should filter entities to those that have property value in specified set"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                                                  | expected
        $.not(company$.id$.nin(1l, 6l, 10l))                                      | [1, 6]
        $.not(company$.name$.nin(["company", "company2", "company8"]))            | [2, 5, 6]
        $.not(company$.registeredAt$.nin(date("2001-01-01"), date("2001-02-02"))) | [5, 6]
    }

    def "negative not in criteria with empty parameter should filter all entities to empty result"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                              | expected
        $.not(company$.id$.nin([]))           | []
        $.not(company$.name$.nin([]))         | []
        $.not(company$.registeredAt$.nin([])) | []
    }

    def "negative not in criteria with null parameters should filter entities to those that have property value in specified set and not null"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                                    | expected
        $.not(company$.id$.nin(1l, null, 10l))                      | [1]
        $.not(company$.name$.nin("company2", "company8", null))     | [2]
        $.not(company$.registeredAt$.nin(date("2001-01-01"), null)) | [5]
    }

    protected Date date(final String input) {
        return Date.parse("yyyy-MM-dd", input)
    }

    protected <E extends Entity<Long>> List<Long> findIds(final Class<E> entityClass, final Query.Criteria criteria) {
        return toIds(repository.query(entityClass).filter(criteria).list());
    }

    protected List<Long> toIds(final List<? extends Entity<Long>> entities) {
        def result = new ArrayList<Long>()
        for (Entity<Long> entity : entities) {
            result.add(entity.id)
        }
        Collections.sort(result)
        return result
    }
}
