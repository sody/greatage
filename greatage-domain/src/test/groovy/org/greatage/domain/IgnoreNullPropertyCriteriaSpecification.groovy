package org.greatage.domain

import org.example.model.Company
import spock.lang.Shared
import spock.lang.Specification

import static org.example.model.$.company$

/**
 * +-------------------------------+
 * |            COMPANY            |
 * +----+----------+---------------+
 * | ID | NAME     | REGISTERED_AT |
 * +----+----------+---------------+
 * | 1  | company1 |          null |
 * | 2  | company2 |    2010-10-10 |
 * | 3  | company3 |          null |
 * | 4  |     null |          null |
 * | 5  |  company |    2001-01-01 |
 * | 6  |  company |    2010-02-02 |
 * +----+----------+---------------+
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
abstract class IgnoreNullPropertyCriteriaSpecification extends Specification {

    @Shared
    protected Repository repository

    def "equal criteria should find only entities with property value equal to specified"() {
        when:
        def actual = findIds(entityClass, criteria)
        then:
        actual == expected

        where:
        entityClass   | criteria                                         | expected
        Company.class | company$.id$.equal(1l)                           | [1]
        Company.class | company$.id$.eq(4l)                              | [4]
        Company.class | company$.id$.eq(10l)                             | []
        Company.class | company$.name$.equal("company")                  | [5, 6]
        Company.class | company$.name$.eq("company1")                    | [1]
        Company.class | company$.name$.eq("company8")                    | []
        Company.class | company$.registeredAt$.equal(date("2001-01-01")) | [5]
        Company.class | company$.registeredAt$.eq(date("2001-01-02"))    | []
    }

    def "equal criteria with null parameter should find only entities with property value equal to null"() {
        when:
        def actual = findIds(entityClass, criteria)
        then:
        actual == expected

        where:
        entityClass   | criteria                        | expected
        Company.class | company$.id$.eq(null)           | []
        Company.class | company$.name$.eq(null)         | [4]
        Company.class | company$.registeredAt$.eq(null) | [1, 3, 4]
    }

    def "not equal criteria should find only entities with property value not equal to specified or null"() {
        when:
        def actual = findIds(entityClass, criteria)
        then:
        actual == expected

        where:
        entityClass   | criteria                                      | expected
        Company.class | company$.id$.notEqual(1l)                     | [2, 3, 4, 5, 6]
        Company.class | company$.id$.ne(10l)                          | [1, 2, 3, 4, 5, 6]
        Company.class | company$.name$.notEqual("company")            | [1, 2, 3, 4]
        Company.class | company$.name$.ne("company1")                 | [2, 3, 4, 5, 6]
        Company.class | company$.name$.ne("company8")                 | [1, 2, 3, 4, 5, 6]
        Company.class | company$.registeredAt$.ne(date("2001-01-01")) | [1, 2, 3, 4, 6]
        Company.class | company$.registeredAt$.ne(date("2001-01-02")) | [1, 2, 3, 4, 5, 6]
    }

    def "not equal criteria with null parameter should find only entities with not null property value"() {
        when:
        def actual = findIds(entityClass, criteria)
        then:
        actual == expected

        where:
        entityClass   | criteria                              | expected
        Company.class | company$.id$.ne(null)                 | [1, 2, 3, 4, 5, 6]
        Company.class | company$.name$.ne(null)               | [1, 2, 3, 5, 6]
        Company.class | company$.registeredAt$.notEqual(null) | [2, 5, 6]
    }

    def "is null criteria should find only entities with property value equal to null"() {
        when:
        def actual = findIds(entityClass, criteria)
        then:
        actual == expected

        where:
        entityClass   | criteria                        | expected
        Company.class | company$.id$.isNull()           | []
        Company.class | company$.id$.eq(null)           | []
        Company.class | company$.name$.isNull()         | [4]
        Company.class | company$.name$.eq(null)         | [4]
        Company.class | company$.registeredAt$.isNull() | [1, 3, 4]
        Company.class | company$.registeredAt$.eq(null) | [1, 3, 4]
    }

    def "is not null criteria should find only entities with property value not equal to null"() {
        when:
        def actual = findIds(entityClass, criteria)
        then:
        actual == expected

        where:
        entityClass   | criteria                         | expected
        Company.class | company$.id$.notNull()           | [1, 2, 3, 4, 5, 6]
        Company.class | company$.id$.ne(null)            | [1, 2, 3, 4, 5, 6]
        Company.class | company$.name$.notNull()         | [1, 2, 3, 5, 6]
        Company.class | company$.name$.ne(null)          | [1, 2, 3, 5, 6]
        Company.class | company$.registeredAt$.notNull() | [2, 5, 6]
        Company.class | company$.registeredAt$.ne(null)  | [2, 5, 6]
    }

    def "greater than criteria should find only entities with property value greater than specified and not null"() {
        when:
        def actual = findIds(entityClass, criteria)
        then:
        actual == expected

        where:
        entityClass   | criteria                                               | expected
        Company.class | company$.id$.greaterThan(4l)                           | [5, 6]
        Company.class | company$.id$.gt(6l)                                    | []
        Company.class | company$.id$.gt(10l)                                   | []
        Company.class | company$.name$.greaterThan("company")                  | [1, 2, 3]
        Company.class | company$.name$.gt("a")                                 | [1, 2, 3, 5, 6]
        Company.class | company$.name$.gt("z")                                 | []
        Company.class | company$.registeredAt$.greaterThan(date("2001-01-01")) | [2, 6]
        Company.class | company$.registeredAt$.gt(date("2010-10-10"))          | []
        Company.class | company$.registeredAt$.gt(date("2012-10-10"))          | []
    }

    def "greater than criteria with null parameter should find only entities with not null property value"() {
        when:
        def actual = findIds(entityClass, criteria)
        then:
        actual == expected

        where:
        entityClass   | criteria                        | expected
        Company.class | company$.id$.gt(null)           | [1, 2, 3, 4, 5, 6]
        Company.class | company$.name$.gt(null)         | [1, 2, 3, 5, 6]
        Company.class | company$.registeredAt$.gt(null) | [2, 5, 6]
    }

    def "greater or equal criteria should find only entities with property value greater or equal to specified and not null"() {
        when:
        def actual = findIds(entityClass, criteria)
        then:
        actual == expected

        where:
        entityClass   | criteria                                                  | expected
        Company.class | company$.id$.greaterOrEqual(4l)                           | [4, 5, 6]
        Company.class | company$.id$.ge(6l)                                       | [6]
        Company.class | company$.id$.ge(10l)                                      | []
        Company.class | company$.name$.greaterOrEqual("company")                  | [1, 2, 3, 5, 6]
        Company.class | company$.name$.ge("a")                                    | [1, 2, 3, 5, 6]
        Company.class | company$.name$.ge("z")                                    | []
        Company.class | company$.registeredAt$.greaterOrEqual(date("2001-01-01")) | [2, 5, 6]
        Company.class | company$.registeredAt$.ge(date("2010-10-10"))             | [2]
        Company.class | company$.registeredAt$.ge(date("2012-10-10"))             | []
    }

    def "greater or equal criteria with null parameter should find all entities"() {
        when:
        def actual = findIds(entityClass, criteria)
        then:
        actual == expected

        where:
        entityClass   | criteria                        | expected
        Company.class | company$.id$.ge(null)           | [1, 2, 3, 4, 5, 6]
        Company.class | company$.name$.ge(null)         | [1, 2, 3, 4, 5, 6]
        Company.class | company$.registeredAt$.ge(null) | [1, 2, 3, 4, 5, 6]
    }

    def "less than criteria should find only entities with property value less than specified or null"() {
        when:
        def actual = findIds(entityClass, criteria)
        then:
        actual == expected

        where:
        entityClass   | criteria                                            | expected
        Company.class | company$.id$.lessThan(4l)                           | [1, 2, 3]
        Company.class | company$.id$.lt(1l)                                 | []
        Company.class | company$.id$.lt(10l)                                | [1, 2, 3, 4, 5, 6]
        Company.class | company$.name$.lessThan("company")                  | [4]
        Company.class | company$.name$.lt("a")                              | [4]
        Company.class | company$.name$.lt("z")                              | [1, 2, 3, 4, 5, 6]
        Company.class | company$.registeredAt$.lessThan(date("2001-01-01")) | [1, 3, 4]
        Company.class | company$.registeredAt$.lt(date("2010-10-10"))       | [1, 3, 4, 5, 6]
        Company.class | company$.registeredAt$.lt(date("2012-10-10"))       | [1, 2, 3, 4, 5, 6]
    }

    def "less than criteria with null parameter should not find anything"() {
        when:
        def actual = findIds(entityClass, criteria)
        then:
        actual == expected

        where:
        entityClass   | criteria                        | expected
        Company.class | company$.id$.lt(null)           | []
        Company.class | company$.name$.lt(null)         | []
        Company.class | company$.registeredAt$.lt(null) | []
    }

    def "less or equal criteria should find only entities with property value less or equal to specified or null"() {
        when:
        def actual = findIds(entityClass, criteria)
        then:
        actual == expected

        where:
        entityClass   | criteria                                               | expected
        Company.class | company$.id$.lessOrEqual(4l)                           | [1, 2, 3, 4]
        Company.class | company$.id$.le(1l)                                    | [1]
        Company.class | company$.id$.le(10l)                                   | [1, 2, 3, 4, 5, 6]
        Company.class | company$.name$.lessOrEqual("company")                  | [4, 5, 6]
        Company.class | company$.name$.le("a")                                 | [4]
        Company.class | company$.name$.le("z")                                 | [1, 2, 3, 4, 5, 6]
        Company.class | company$.registeredAt$.lessOrEqual(date("2001-01-01")) | [1, 3, 4, 5]
        Company.class | company$.registeredAt$.le(date("2010-10-10"))          | [1, 2, 3, 4, 5, 6]
        Company.class | company$.registeredAt$.le(date("2012-10-10"))          | [1, 2, 3, 4, 5, 6]
    }

    def "less or equal criteria with null parameter should find only entities with property value equal to null"() {
        when:
        def actual = findIds(entityClass, criteria)
        then:
        actual == expected

        where:
        entityClass   | criteria                        | expected
        Company.class | company$.id$.le(null)           | []
        Company.class | company$.name$.le(null)         | [4]
        Company.class | company$.registeredAt$.le(null) | [1, 3, 4]
    }

    def "in criteria should find only entities with property value in specified set"() {
        when:
        def actual = findIds(entityClass, criteria)
        then:
        actual == expected

        where:
        entityClass   | criteria                                                                              | expected
        Company.class | company$.id$.in(1l, 2l, 3l)                                                           | [1, 2, 3]
        Company.class | company$.id$.in(1l)                                                                   | [1]
        Company.class | company$.id$.in(10l)                                                                  | []
        Company.class | company$.id$.in(1l, 6l, 10l)                                                          | [1, 6]
        Company.class | company$.name$.in("company", "company1")                                              | [1, 5, 6]
        Company.class | company$.name$.in("company")                                                          | [5, 6]
        Company.class | company$.name$.in("company8")                                                         | []
        Company.class | company$.name$.in("company2", "company8")                                             | [2]
        Company.class | company$.registeredAt$.in(date("2001-01-01"), date("2001-02-02"))                     | [5, 6]
        Company.class | company$.registeredAt$.in(date("2012-10-10"))                                         | []
        Company.class | company$.registeredAt$.in(date("2001-01-01"), date("2010-10-10"), date("1999-01-01")) | [2, 5]
    }

    def "in criteria with empty parameter should not find anything"() {
        when:
        def actual = findIds(entityClass, criteria)
        then:
        actual == expected

        where:
        entityClass   | criteria                      | expected
        Company.class | company$.id$.in([])           | []
        Company.class | company$.name$.in([])         | []
        Company.class | company$.registeredAt$.in([]) | []
    }

    def "in criteria with null parameters should find only entities with property value in specified set or null"() {
        when:
        def actual = findIds(entityClass, criteria)
        then:
        actual == expected

        where:
        entityClass   | criteria                                            | expected
        Company.class | company$.id$.in([null])                             | []
        Company.class | company$.id$.in(1l, null, 10l)                      | [1]
        Company.class | company$.name$.in([null])                           | [4]
        Company.class | company$.name$.in("company2", "company8", null)     | [2, 4]
        Company.class | company$.registeredAt$.in([null])                   | [1, 3, 4]
        Company.class | company$.registeredAt$.in(date("2001-01-01"), null) | [1, 3, 4, 5]
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
