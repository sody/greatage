package org.greatage.domain

import org.example.model.Company
import spock.lang.Shared
import spock.lang.Specification

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
abstract class PaginationSpecification extends Specification {
    private static final Query.Property ID_PROPERTY = new Query.Property() {
        String getPath() {
            return null
        }

        String getProperty() {
            return "id"
        }
    }

    @Shared
    protected Repository repository

    def "default pagination should retrieve all entities"() {
        when:
        def actual = findIds(entityClass)
        then:
        actual == expected

        where:
        entityClass   | expected
        Company.class | [1, 2, 3, 4, 5, 6]
    }

    def "pagination with zero or negative start point should not limit retrieved entities"() {
        when:
        def actual = findIds(entityClass, start, count)
        then:
        actual == expected

        where:
        entityClass   | start | count | expected
        Company.class | 0     | -1    | [1, 2, 3, 4, 5, 6]
        Company.class | -1    | 6     | [1, 2, 3, 4, 5, 6]
        Company.class | -10   | 3     | [1, 2, 3]
    }

    def "pagination with negative count should not limit retrieved entities"() {
        when:
        def actual = findIds(entityClass, start, count)
        then:
        actual == expected

        where:
        entityClass   | start | count | expected
        Company.class | 0     | -1    | [1, 2, 3, 4, 5, 6]
        Company.class | 3     | -1    | [4, 5, 6]
        Company.class | 5     | -10   | [6]
    }

    def "pagination with zero count should not retrieve any entities"() {
        when:
        def actual = findIds(entityClass, start, count)
        then:
        actual == expected

        where:
        entityClass   | start | count | expected
        Company.class | 0     | 0     | []
        Company.class | 3     | 0     | []
        Company.class | 5     | 0     | []
    }

    def "pagination with defined start point should retrieve entities starting from specified one"() {
        when:
        def actual = findIds(entityClass, start, count)
        then:
        actual == expected

        where:
        entityClass   | start | count | expected
        Company.class | 0     | -1    | [1, 2, 3, 4, 5, 6]
        Company.class | 2     | 3     | [3, 4, 5]
        Company.class | 5     | 5     | [6]
        Company.class | 8     | -1    | []
    }

    def "pagination with defined count should retrieve entities limited to specified number"() {
        when:
        def actual = findIds(entityClass, start, count)
        then:
        actual == expected

        where:
        entityClass   | start | count | expected
        Company.class | 0     | 0     | []
        Company.class | 1     | 1     | [2]
        Company.class | 3     | 2     | [4, 5]
        Company.class | 0     | 5     | [1, 2, 3, 4, 5]
        Company.class | 5     | 8     | [6]
    }

    protected <E extends Entity<Long>> List<Long> findIds(final Class<E> entityClass) {

        return toIds(repository.query(entityClass)
                .sort(ID_PROPERTY, true, false)
                .list())
    }

    protected <E extends Entity<Long>> List<Long> findIds(final Class<E> entityClass,
                                                          final int start,
                                                          final int count) {

        return toIds(repository.query(entityClass)
                .sort(ID_PROPERTY, true, false)
                .paginate(start, count)
                .list())
    }

    protected List<Long> toIds(final List<? extends Entity<Long>> entities) {
        def result = new ArrayList<Long>()
        for (Entity<Long> entity : entities) {
            result.add(entity.id)
        }
        return result
    }
}
