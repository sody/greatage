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
abstract class JunctionCriteriaSpecification extends Specification {

    @Shared
    protected Repository repository

    def "all criteria should find all entities"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria       | expected
        company$.all() | [1, 2, 3, 4, 5, 6]
    }

    def "empty junction criteria should find all entities"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria       | expected
        $.and()        | [1, 2, 3, 4, 5, 6]
        $.or()         | [1, 2, 3, 4, 5, 6]
        $.and($.and()) | [1, 2, 3, 4, 5, 6]
        $.or($.or())   | [1, 2, 3, 4, 5, 6]
        $.and($.or())  | [1, 2, 3, 4, 5, 6]
        $.or($.and())  | [1, 2, 3, 4, 5, 6]
    }

    def "and criteria should find only entities that match each child criteria"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                                                                                | expected
        $.and(company$.name$.eq("company1"), company$.id$.eq(1l))                                               | [1]
        $.and(company$.name$.eq("company1"), company$.id$.eq(2l))                                               | []
        $.and(company$.name$.eq("company"), company$.id$.le(6l))                                                | [5, 6]
        $.and(company$.name$.eq("company"), company$.id$.lt(5l))                                                | []
        $.and(company$.name$.notNull(), company$.id$.eq(4l))                                                    | []
        $.and(company$.name$.eq("company"), company$.id$.gt(2l), company$.registeredAt$.eq(date("2001-01-01"))) | [5]
        $.and(company$.name$.eq("company"), company$.id$.gt(2l), company$.registeredAt$.eq(date("2001-02-02"))) | [6]
        $.and(company$.name$.eq("company"), company$.id$.gt(2l), company$.registeredAt$.eq(date("2011-02-02"))) | []
    }

    def "and criteria inside another and criteria should find only entities that match each criteria"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                                                                                       | expected
        $.and(company$.name$.eq("company"), $.and(company$.id$.gt(2l), company$.registeredAt$.eq(date("2001-01-01")))) | [5]
        $.and($.and(company$.name$.eq("company"), company$.id$.gt(2l), company$.registeredAt$.eq(date("2001-02-02")))) | [6]
        $.and(company$.name$.eq("company"), $.and(company$.id$.gt(2l), company$.registeredAt$.eq(date("2011-02-02")))) | []
    }

    def "or criteria should find only entities that match at least one child criteria"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                                                                               | expected
        $.or(company$.name$.eq("company1"), company$.id$.eq(1l))                                               | [1]
        $.or(company$.name$.eq("company1"), company$.id$.eq(2l))                                               | [1, 2]
        $.or(company$.name$.eq("company"), company$.id$.le(6l))                                                | [1, 2, 3, 4, 5, 6]
        $.or(company$.name$.eq("company"), company$.id$.lt(5l))                                                | [1, 2, 3, 4, 5, 6]
        $.or(company$.name$.notNull(), company$.id$.eq(4l))                                                    | [1, 2, 3, 4, 5, 6]
        $.or(company$.name$.eq("company"), company$.id$.eq(1l), company$.registeredAt$.eq(date("2010-10-10"))) | [1, 2, 5, 6]
    }

    def "or criteria inside another or criteria should find only entities that match at least one criteria"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                                                                                     | expected
        $.or(company$.name$.eq("company"), $.or(company$.id$.gt(2l), company$.registeredAt$.eq(date("2001-01-01")))) | [3, 4, 5, 6]
        $.or($.or(company$.name$.eq("company"), company$.id$.eq(2l), company$.registeredAt$.eq(date("2001-02-02")))) | [2, 5, 6]
        $.or(company$.name$.ne("company"), $.or(company$.id$.eq(2l), company$.registeredAt$.eq(date("2001-02-02")))) | [1, 2, 3, 6]
    }

    def "and criteria inside or criteria should find only entities that match each child criteria or at least one sibling criteria"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                                                                                      | expected
        $.or(company$.name$.eq("company"), $.and(company$.id$.eq(2l), company$.registeredAt$.eq(date("2010-10-10")))) | [2, 5, 6]
        $.or($.and(company$.name$.eq("company"), company$.id$.gt(5l)), company$.registeredAt$.eq(date("2010-10-10"))) | [2, 6]
        $.or(company$.name$.eq("company"), $.and(company$.id$.gt(2l), company$.registeredAt$.eq(date("2011-02-02")))) | [5, 6]
        $.or($.and(company$.id$.gt(2l), company$.registeredAt$.eq(date("2011-02-02"))))                               | []
    }

    def "or criteria inside and criteria should find only entities that match at least one child criteria and each sibling criteria"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                                                                                      | expected
        $.and(company$.name$.eq("company"), $.or(company$.id$.eq(2l), company$.registeredAt$.eq(date("2010-10-10")))) | []
        $.and($.or(company$.name$.eq("company"), company$.id$.gt(5l), company$.registeredAt$.eq(date("2010-10-10")))) | [2, 5, 6]
        $.and(company$.name$.eq("company"), $.or(company$.id$.gt(5l), company$.id$.lt(5l)))                           | [6]
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
