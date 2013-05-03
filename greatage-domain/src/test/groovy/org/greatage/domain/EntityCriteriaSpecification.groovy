package org.greatage.domain

import org.example.model.Department
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
 * +---------------------------------+
 * |            DEPARTMENT           |
 * +----+---------------+------------+
 * | ID |      NAME     | COMPANY_ID |
 * +----+---------------+------------+
 * |  7 |   department7 |       null |
 * |  9 |          null |       null |
 * | 11 | department1_1 |          1 |
 * | 12 | department1_2 |          1 |
 * | 13 | department1_3 |          1 |
 * | 41 | department4_1 |          4 |
 * | 42 | department4_2 |          4 |
 * +----+---------------+------------+
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
abstract class EntityCriteriaSpecification extends Specification {

    @Shared
    protected Repository repository

    def "entity all criteria should find all entities"() {
        when:
        def actual = findIds(Department.class, criteria)
        then:
        actual == expected

        where:
        criteria                                            | expected
        department$.company$.all()                          | [7, 9, 11, 12, 13, 41, 42]
        department$.company$.is(company$.all())             | [7, 9, 11, 12, 13, 41, 42]
        simpleDepartment$.company$.is(simpleCompany$.all()) | [7, 9, 11, 12, 13, 41, 42]
    }

    def "entity property criteria should find only entities that has not null association and match property criteria"() {
        when:
        def actual = findIds(Department.class, criteria)
        then:
        actual == expected

        where:
        criteria                                                               | expected
        department$.company$.name$.eq("company1")                              | [11, 12, 13]
        department$.company$.id$.ne(1l)                                        | [41, 42]
        department$.company$.name$.isNull()                                    | [41, 42]
        department$.company$.registeredAt$.notNull()                           | []
        department$.company$.name$.gt("company")                               | [11, 12, 13]
        department$.company$.name$.ge("company1")                              | [11, 12, 13]
        department$.company$.id$.lt(4l)                                        | [11, 12, 13]
        department$.company$.id$.le(1l)                                        | [11, 12, 13]
        department$.company$.id$.in([])                                        | []
        department$.company$.id$.in([2l])                                      | []
        department$.company$.id$.in([1l, 2l, 3l, 4l])                          | [11, 12, 13, 41, 42]
        department$.company$.is(company$.name$.eq("company1"))                 | [11, 12, 13]
        department$.company$.is(company$.id$.ne(1l))                           | [41, 42]
        department$.company$.is(company$.name$.isNull())                       | [41, 42]
        department$.company$.is(company$.registeredAt$.notNull())              | []
        department$.company$.is(company$.name$.gt("company"))                  | [11, 12, 13]
        department$.company$.is(company$.name$.ge("company1"))                 | [11, 12, 13]
        department$.company$.is(company$.id$.lt(4l))                           | [11, 12, 13]
        department$.company$.is(company$.id$.le(1l))                           | [11, 12, 13]
        department$.company$.is(company$.id$.in([]))                           | []
        department$.company$.is(company$.id$.in([2l]))                         | []
        department$.company$.is(company$.id$.in([1l, 2l, 3l, 4l]))             | [11, 12, 13, 41, 42]
        simpleDepartment$.company$.is(simpleCompany$.name$.eq("company1"))     | [11, 12, 13]
        simpleDepartment$.company$.is(simpleCompany$.id$.ne(1l))               | [41, 42]
        simpleDepartment$.company$.is(simpleCompany$.name$.isNull())           | [41, 42]
        simpleDepartment$.company$.is(simpleCompany$.registeredAt$.notNull())  | []
        simpleDepartment$.company$.is(simpleCompany$.name$.gt("company"))      | [11, 12, 13]
        simpleDepartment$.company$.is(simpleCompany$.name$.ge("company1"))     | [11, 12, 13]
        simpleDepartment$.company$.is(simpleCompany$.id$.lt(4l))               | [11, 12, 13]
        simpleDepartment$.company$.is(simpleCompany$.id$.le(1l))               | [11, 12, 13]
        simpleDepartment$.company$.is(simpleCompany$.id$.in([]))               | []
        simpleDepartment$.company$.is(simpleCompany$.id$.in([2l]))             | []
        simpleDepartment$.company$.is(simpleCompany$.id$.in([1l, 2l, 3l, 4l])) | [11, 12, 13, 41, 42]
    }

    def "entity embed criteria should find only entities that has not null association and match embed criteria"() {
        when:
        def actual = findIds(Department.class, criteria)
        then:
        actual == expected

        where:
        criteria                                                                                   | expected
        department$.company$.info$.code$.eq("C4")                                                  | [41, 42]
        department$.company$.info$.code$.isNull()                                                  | [11, 12, 13]
        department$.company$.info$.code$.notNull()                                                 | [41, 42]
        department$.company$.is(company$.info$.code$.eq("C4"))                                     | [41, 42]
        department$.company$.is(company$.info$.code$.isNull())                                     | [11, 12, 13]
        department$.company$.is(company$.info$.code$.notNull())                                    | [41, 42]
        department$.company$.is(company$.info$.is(companyInfo$.code$.eq("C4")))                    | [41, 42]
        department$.company$.is(company$.info$.is(companyInfo$.code$.isNull()))                    | [11, 12, 13]
        department$.company$.is(company$.info$.is(companyInfo$.code$.notNull()))                   | [41, 42]
        simpleDepartment$.company$.is(simpleCompany$.info$.is(simpleCompanyInfo$.code$.eq("C4")))  | [41, 42]
        simpleDepartment$.company$.is(simpleCompany$.info$.is(simpleCompanyInfo$.code$.isNull()))  | [11, 12, 13]
        simpleDepartment$.company$.is(simpleCompany$.info$.is(simpleCompanyInfo$.code$.notNull())) | [41, 42]
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
