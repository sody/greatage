package org.greatage.domain

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
abstract class PropertyCriteriaSpecification extends Specification {

    @Shared
    protected Repository repository

    def "equal criteria should filter entities to those that have property value equal to specified"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                         | expected
        company$.id$.equal(1l)                           | [1]
        company$.id$.eq(4l)                              | [4]
        company$.id$.eq(10l)                             | []
        company$.name$.equal("company")                  | [5, 6]
        company$.name$.eq("company1")                    | [1]
        company$.name$.eq("company8")                    | []
        company$.registeredAt$.equal(date("2001-01-01")) | [5]
        company$.registeredAt$.eq(date("2001-01-02"))    | []
    }

    def "equal criteria with null parameter should filter entities to those that have property value equal to null"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                           | expected
        company$.id$.eq(null)              | []
        company$.id$.equal(null)           | []
        company$.name$.eq(null)            | [4]
        company$.name$.equal(null)         | [4]
        company$.registeredAt$.eq(null)    | [1, 3, 4]
        company$.registeredAt$.equal(null) | [1, 3, 4]
    }

    def "not equal criteria should filter entities to those that have property value not equal to specified and not null"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                      | expected
        company$.id$.notEqual(1l)                     | [2, 3, 4, 5, 6]
        company$.id$.ne(10l)                          | [1, 2, 3, 4, 5, 6]
        company$.name$.notEqual("company")            | [1, 2, 3]
        company$.name$.ne("company1")                 | [2, 3, 5, 6]
        company$.name$.ne("company8")                 | [1, 2, 3, 5, 6]
        company$.registeredAt$.ne(date("2001-01-01")) | [2, 6]
        company$.registeredAt$.ne(date("2001-01-02")) | [2, 5, 6]
    }

    def "not equal criteria with null parameter should filter entities to those that have not null property value"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                              | expected
        company$.id$.ne(null)                 | [1, 2, 3, 4, 5, 6]
        company$.id$.notEqual(null)           | [1, 2, 3, 4, 5, 6]
        company$.name$.ne(null)               | [1, 2, 3, 5, 6]
        company$.name$.notEqual(null)         | [1, 2, 3, 5, 6]
        company$.registeredAt$.ne(null)       | [2, 5, 6]
        company$.registeredAt$.notEqual(null) | [2, 5, 6]
    }

    def "is null criteria should filter entities to those that have property value equal to null"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                        | expected
        company$.id$.isNull()           | []
        company$.name$.isNull()         | [4]
        company$.registeredAt$.isNull() | [1, 3, 4]
    }

    def "is not null criteria should filter entities to those that have property value not equal to null"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                         | expected
        company$.id$.notNull()           | [1, 2, 3, 4, 5, 6]
        company$.name$.notNull()         | [1, 2, 3, 5, 6]
        company$.registeredAt$.notNull() | [2, 5, 6]
    }

    def "greater than criteria should filter entities to those that have property value greater than specified and not null"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                               | expected
        company$.id$.greaterThan(4l)                           | [5, 6]
        company$.id$.gt(6l)                                    | []
        company$.id$.gt(10l)                                   | []
        company$.name$.greaterThan("company")                  | [1, 2, 3]
        company$.name$.gt("a")                                 | [1, 2, 3, 5, 6]
        company$.name$.gt("z")                                 | []
        company$.registeredAt$.greaterThan(date("2001-01-01")) | [2, 6]
        company$.registeredAt$.gt(date("2010-10-10"))          | []
        company$.registeredAt$.gt(date("2012-10-10"))          | []
    }

    def "greater than criteria with null parameter should filter all entities to empty result"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                 | expected
        company$.id$.gt(null)                    | []
        company$.id$.greaterThan(null)           | []
        company$.name$.gt(null)                  | []
        company$.name$.greaterThan(null)         | []
        company$.registeredAt$.gt(null)          | []
        company$.registeredAt$.greaterThan(null) | []
    }

    def "greater or equal criteria should filter entities to those that have property value greater or equal to specified and not null"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                                  | expected
        company$.id$.greaterOrEqual(4l)                           | [4, 5, 6]
        company$.id$.ge(6l)                                       | [6]
        company$.id$.ge(10l)                                      | []
        company$.name$.greaterOrEqual("company")                  | [1, 2, 3, 5, 6]
        company$.name$.ge("a")                                    | [1, 2, 3, 5, 6]
        company$.name$.ge("z")                                    | []
        company$.registeredAt$.greaterOrEqual(date("2001-01-01")) | [2, 5, 6]
        company$.registeredAt$.ge(date("2010-10-10"))             | [2]
        company$.registeredAt$.ge(date("2012-10-10"))             | []
    }

    def "greater or equal criteria with null parameter should filter all entities to empty result"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                    | expected
        company$.id$.ge(null)                       | []
        company$.id$.greaterOrEqual(null)           | []
        company$.name$.ge(null)                     | []
        company$.name$.greaterOrEqual(null)         | []
        company$.registeredAt$.ge(null)             | []
        company$.registeredAt$.greaterOrEqual(null) | []
    }

    def "less than criteria should filter entities to those that have property value less than specified and not null"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                            | expected
        company$.id$.lessThan(4l)                           | [1, 2, 3]
        company$.id$.lt(1l)                                 | []
        company$.id$.lt(10l)                                | [1, 2, 3, 4, 5, 6]
        company$.name$.lessThan("company")                  | []
        company$.name$.lt("a")                              | []
        company$.name$.lt("z")                              | [1, 2, 3, 5, 6]
        company$.registeredAt$.lessThan(date("2001-01-01")) | []
        company$.registeredAt$.lt(date("2010-10-10"))       | [5, 6]
        company$.registeredAt$.lt(date("2012-10-10"))       | [2, 5, 6]
    }

    def "less than criteria with null parameter should filter all entities to empty result"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                              | expected
        company$.id$.lt(null)                 | []
        company$.id$.lessThan(null)           | []
        company$.name$.lt(null)               | []
        company$.name$.lessThan(null)         | []
        company$.registeredAt$.lt(null)       | []
        company$.registeredAt$.lessThan(null) | []
    }

    def "less or equal criteria should filter entities to those that have property value less or equal to specified and not null"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                               | expected
        company$.id$.lessOrEqual(4l)                           | [1, 2, 3, 4]
        company$.id$.le(1l)                                    | [1]
        company$.id$.le(10l)                                   | [1, 2, 3, 4, 5, 6]
        company$.name$.lessOrEqual("company")                  | [5, 6]
        company$.name$.le("a")                                 | []
        company$.name$.le("z")                                 | [1, 2, 3, 5, 6]
        company$.registeredAt$.lessOrEqual(date("2001-01-01")) | [5]
        company$.registeredAt$.le(date("2010-10-10"))          | [2, 5, 6]
        company$.registeredAt$.le(date("2012-10-10"))          | [2, 5, 6]
    }

    def "less or equal with null parameter criteria should filter all entities to empty result"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                 | expected
        company$.id$.le(null)                    | []
        company$.id$.lessOrEqual(null)           | []
        company$.name$.le(null)                  | []
        company$.name$.lessOrEqual(null)         | []
        company$.registeredAt$.le(null)          | []
        company$.registeredAt$.lessOrEqual(null) | []
    }

    def "in criteria should filter entities to those that have property value in specified set"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                                                              | expected
        company$.id$.in(1l, 2l, 3l)                                                           | [1, 2, 3]
        company$.id$.in(1l)                                                                   | [1]
        company$.id$.in(10l)                                                                  | []
        company$.id$.in(1l, 6l, 10l)                                                          | [1, 6]
        company$.name$.in("company", "company1")                                              | [1, 5, 6]
        company$.name$.in("company")                                                          | [5, 6]
        company$.name$.in("company8")                                                         | []
        company$.name$.in("company2", "company8")                                             | [2]
        company$.registeredAt$.in(date("2001-01-01"), date("2001-02-02"))                     | [5, 6]
        company$.registeredAt$.in(date("2012-10-10"))                                         | []
        company$.registeredAt$.in(date("2001-01-01"), date("2010-10-10"), date("1999-01-01")) | [2, 5]
    }

    def "in criteria with empty parameter should filter all entities to empty result"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                      | expected
        company$.id$.in([])           | []
        company$.name$.in([])         | []
        company$.registeredAt$.in([]) | []
    }

    //todo: is that correct? should any exception be thrown?
    def "in criteria with null parameters should filter entities to those that have property value in specified set and not null"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                            | expected
        company$.id$.in([null])                             | []
        company$.id$.in(1l, null, 10l)                      | [1]
        company$.name$.in([null])                           | []
        company$.name$.in("company2", "company8", null)     | [2]
        company$.registeredAt$.in([null])                   | []
        company$.registeredAt$.in(date("2001-01-01"), null) | [5]
    }

    def "not in criteria should filter entities to those that have property value not in specified set and not null"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                                                               | expected
        company$.id$.nin([1l, 2l, 3l])                                                         | [4, 5, 6]
        company$.id$.notIn(1l)                                                                 | [2, 3, 4, 5, 6]
        company$.id$.notIn(10l)                                                                | [1, 2, 3, 4, 5, 6]
        company$.id$.nin(1l, 6l, 10l)                                                          | [2, 3, 4, 5]
        company$.name$.notIn("company", "company1")                                            | [2, 3]
        company$.name$.nin("company")                                                          | [1, 2, 3]
        company$.name$.nin("company8")                                                         | [1, 2, 3, 5, 6]
        company$.name$.notIn(["company2", "company8"])                                         | [1, 3, 5, 6]
        company$.registeredAt$.nin(date("2001-01-01"), date("2001-02-02"))                     | [2]
        company$.registeredAt$.nin(date("2012-10-10"))                                         | [2, 5, 6]
        company$.registeredAt$.nin(date("2001-01-01"), date("2010-10-10"), date("1999-01-01")) | [6]
    }

    def "not in criteria with empty parameter should not filter entities"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                       | expected
        company$.id$.nin([])           | [1, 2, 3, 4, 5, 6]
        company$.name$.nin([])         | [1, 2, 3, 4, 5, 6]
        company$.registeredAt$.nin([]) | [1, 2, 3, 4, 5, 6]
    }

    //todo: is that correct? should any exception be thrown?
    def "not in criteria with null parameters should filter entities to those that have property value not in specified set and not null"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                            | expected
        company$.id$.nin([null])                             | [1, 2, 3, 4, 5, 6]
        company$.id$.nin(1l, null, 10l)                      | [2, 3, 4, 5, 6]
        company$.name$.nin([null])                           | [1, 2, 3, 5, 6]
        company$.name$.nin("company2", "company8", null)     | [1, 3, 5, 6]
        company$.registeredAt$.nin([null])                   | [2, 5, 6]
        company$.registeredAt$.nin(date("2001-01-01"), null) | [2, 6]
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
