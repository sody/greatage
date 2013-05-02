package org.greatage.domain

import org.example.model.Company
import spock.lang.Shared
import spock.lang.Specification

import static org.example.criteria.$.*

/**
 * +-------------------------------+----------------+
 * |            COMPANY            |  COMPANY_INFO  |
 * +----+----------+---------------+----------------+
 * | ID |   NAME   | REGISTERED_AT |    INFO_CODE   |
 * +----+----------+---------------+----------------+
 * | 1  | company1 |          null |           null |
 * | 2  | company2 |    2010-10-10 |             C2 |
 * | 3  | company3 |          null |             C3 |
 * | 4  |     null |          null |             C4 |
 * | 5  |  company |    2001-01-01 |           null |
 * | 6  |  company |    2001-02-02 |           null |
 * +----+----------+---------------+----------------+
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
abstract class EmbedCriteriaSpecification extends Specification {

    @Shared
    protected Repository repository

    def "embed all criteria should find all entities"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                          | expected
        company$.info$.all()                              | [1, 2, 3, 4, 5, 6]
        company$.info$.is(companyInfo$.all())             | [1, 2, 3, 4, 5, 6]
        simpleCompany$.info$.is(simpleCompanyInfo$.all()) | [1, 2, 3, 4, 5, 6]
    }

    def "embed property criteria should find only entities that match"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                                               | expected
        company$.info$.code$.eq("C2")                                          | [2]
        company$.info$.code$.eq("C8")                                          | []
        company$.info$.code$.ne("C4")                                          | [2, 3]
        company$.info$.code$.isNull()                                          | [1, 5, 6]
        company$.info$.code$.notNull()                                         | [2, 3, 4]
        company$.info$.code$.in("C2", "C4", "C8")                              | [2, 4]
        company$.info$.is(companyInfo$.code$.eq("C2"))                         | [2]
        company$.info$.is(companyInfo$.code$.eq("C8"))                         | []
        company$.info$.is(companyInfo$.code$.ne("C4"))                         | [2, 3]
        company$.info$.is(companyInfo$.code$.isNull())                         | [1, 5, 6]
        company$.info$.is(companyInfo$.code$.notNull())                        | [2, 3, 4]
        company$.info$.is(companyInfo$.code$.in("C2", "C4", "C8"))             | [2, 4]
        simpleCompany$.info$.is(simpleCompanyInfo$.code$.eq("C2"))             | [2]
        simpleCompany$.info$.is(simpleCompanyInfo$.code$.eq("C8"))             | []
        simpleCompany$.info$.is(simpleCompanyInfo$.code$.ne("C4"))             | [2, 3]
        simpleCompany$.info$.is(simpleCompanyInfo$.code$.isNull())             | [1, 5, 6]
        simpleCompany$.info$.is(simpleCompanyInfo$.code$.notNull())            | [2, 3, 4]
        simpleCompany$.info$.is(simpleCompanyInfo$.code$.in("C2", "C4", "C8")) | [2, 4]
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
