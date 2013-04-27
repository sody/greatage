package org.greatage.domain.objectify

import org.example.model.$
import org.example.model.Company
import org.greatage.domain.Entity
import org.greatage.domain.Query
import org.greatage.domain.Repository
import spock.lang.Shared
import spock.lang.Specification

import static org.example.model.$.company$

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
class ObjectifyJunctionCriteriaSpec extends Specification {

    @Shared
    private ObjectifyTestData data = new ObjectifyTestData();

    @Shared
    private Repository repository

    def setupSpec() {
        repository = data.setup()
    }

    def cleanupSpec() {
        repository = data.cleanup()
    }

    def "all criteria should find all entities"() {
        when:
        def actual = findIds(entityClass, criteria)
        then:
        actual == expected

        where:
        entityClass   | criteria       | expected
        Company.class | company$.all() | [1, 2, 3, 4, 5, 6]
    }

    def "and criteria should find only entities than match each child criteria"() {
        when:
        def actual = findIds(entityClass, criteria)
        then:
        actual == expected

        where:
        entityClass   | criteria                                                                                                | expected
        Company.class | $.and(company$.name$.eq("company1"), company$.id$.eq(1l))                                               | [1]
        Company.class | $.and(company$.name$.eq("company1"), company$.id$.eq(2l))                                               | []
        Company.class | $.and(company$.name$.eq("company"), company$.id$.le(6l))                                                | [5, 6]
        Company.class | $.and(company$.name$.eq("company"), company$.id$.lt(5l))                                                | []
        Company.class | $.and(company$.name$.notNull(), company$.id$.eq(4l))                                                    | []
        Company.class | $.and(company$.name$.eq("company"), company$.id$.gt(2l), company$.registeredAt$.eq(date("2001-01-01"))) | [5]
        Company.class | $.and(company$.name$.eq("company"), company$.id$.gt(2l), company$.registeredAt$.eq(date("2001-02-02"))) | [6]
        Company.class | $.and(company$.name$.eq("company"), company$.id$.gt(2l), company$.registeredAt$.eq(date("2011-02-02"))) | []
    }

    def "or criteria is not supported"() {
        when:
        findIds(entityClass, criteria)
        then:
        thrown(UnsupportedOperationException)

        where:
        entityClass   | criteria
        Company.class | $.or(company$.name$.eq("company1"), company$.id$.eq(1l))
        Company.class | $.or(company$.name$.eq("company1"), company$.id$.eq(2l))
        Company.class | $.or(company$.name$.eq("company"), company$.id$.le(6l))
        Company.class | $.or(company$.name$.eq("company"), company$.id$.lt(5l))
        Company.class | $.or(company$.name$.notNull(), company$.id$.eq(4l))
        Company.class | $.or(company$.name$.eq("company"), company$.id$.eq(1l), company$.registeredAt$.eq(date("2010-10-10")))
    }

    protected Date date(final String input) {
        return Date.parse("yyyy-MM-dd", input)
    }

    protected <E extends Entity<Long>> List<Long> findIds(final Class<E> entityClass, final Query.Criteria<Long, E> criteria) {
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
