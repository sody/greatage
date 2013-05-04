package org.greatage.domain

import org.example.criteria._$
import org.example.model.Company
import org.example.model.Department
import spock.lang.Shared
import spock.lang.Specification

import static org.example.criteria.$.*

/**
 * +---------------------+
 * |       COUNTRY       |
 * +----+------+---------+
 * | ID | CODE |   NAME  |
 * +----+------+---------+
 * |  1 |   us |     USA |
 * |  2 |   ru |  Russia |
 * |  3 |   by |    null |
 * +----+------+---------+
 *
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
 * +---------------------------------+
 * |            DEPARTMENT           |
 * +----+---------------+------------+
 * | ID |      NAME     | COMPANY_ID |
 * +----+---------------+------------+
 * |  2 |   department2 |          2 |
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

    def "all criteria inside entity criteria should not filter entities"() {
        when:
        def actual = findIds(Department.class, criteria)
        then:
        actual == expected

        where:
        criteria                                      | expected
        department$.company$.all()                    | [2, 7, 9, 11, 12, 13, 41, 42]
        department$.company$.is(company$.all())       | [2, 7, 9, 11, 12, 13, 41, 42]
        _$.department$.company$.is(_$.company$.all()) | [2, 7, 9, 11, 12, 13, 41, 42]
    }

    def "property criteria inside entity criteria should filter entities to those that have not null association and match property expression for it"() {
        when:
        def actual = findIds(Department.class, criteria)
        then:
        actual == expected

        where:
        criteria                                                                               | expected
        department$.company$.name$.eq("company1")                                              | [11, 12, 13]
        department$.company$.name$.isNull()                                                    | [41, 42]
        department$.company$.name$.in(["company0", "company1", "company2"])                    | [2, 11, 12, 13]
        department$.company$.is(company$.name$.eq("company1"))                                 | [11, 12, 13]
        department$.company$.is(company$.name$.isNull())                                       | [41, 42]
        department$.company$.is(company$.name$.in(["company0", "company1", "company2"]))       | [2, 11, 12, 13]
        _$.department$.company$.is(_$.company$.name$.eq("company1"))                           | [11, 12, 13]
        _$.department$.company$.is(_$.company$.name$.isNull())                                 | [41, 42]
        _$.department$.company$.is(_$.company$.name$.in(["company0", "company1", "company2"])) | [2, 11, 12, 13]
    }

    def "embed criteria inside entity criteria should filter entities to those that have not null association and match embed criteria for it"() {
        when:
        def actual = findIds(Department.class, criteria)
        then:
        actual == expected

        where:
        criteria                                                                          | expected
        department$.company$.info$.code$.eq("C4")                                         | [41, 42]
        department$.company$.info$.code$.isNull()                                         | [11, 12, 13]
        department$.company$.info$.code$.notNull()                                        | [2, 41, 42]
        department$.company$.info$.is(companyInfo$.code$.eq("C4"))                        | [41, 42]
        department$.company$.info$.is(companyInfo$.code$.isNull())                        | [11, 12, 13]
        department$.company$.info$.is(companyInfo$.code$.notNull())                       | [2, 41, 42]
        department$.company$.is(company$.info$.code$.eq("C4"))                            | [41, 42]
        department$.company$.is(company$.info$.code$.isNull())                            | [11, 12, 13]
        department$.company$.is(company$.info$.code$.notNull())                           | [2, 41, 42]
        department$.company$.is(company$.info$.is(companyInfo$.code$.eq("C4")))           | [41, 42]
        department$.company$.is(company$.info$.is(companyInfo$.code$.isNull()))           | [11, 12, 13]
        department$.company$.is(company$.info$.is(companyInfo$.code$.notNull()))          | [2, 41, 42]
        _$.department$.company$.is(_$.company$.info$.is(_$.companyInfo$.code$.eq("C4")))  | [41, 42]
        _$.department$.company$.is(_$.company$.info$.is(_$.companyInfo$.code$.isNull()))  | [11, 12, 13]
        _$.department$.company$.is(_$.company$.info$.is(_$.companyInfo$.code$.notNull())) | [2, 41, 42]
    }

    def "multi-level embed criteria inside entity criteria should filter entities to those that have not null association and match embed criteria for it"() {
        when:
        def actual = findIds(Department.class, criteria)
        then:
        actual == expected

        where:
        criteria                                                                                                      | expected
        department$.company$.info$.address$.address1$.isNull()                                                        | [11, 12, 13, 41, 42]
        department$.company$.info$.address$.city$.eq("city_2")                                                        | [2]
        department$.company$.info$.address$.is(address$.address1$.isNull())                                           | [11, 12, 13, 41, 42]
        department$.company$.info$.address$.is(address$.city$.eq("city_2"))                                           | [2]
        department$.company$.info$.is(companyInfo$.address$.address1$.isNull())                                       | [11, 12, 13, 41, 42]
        department$.company$.info$.is(companyInfo$.address$.city$.eq("city_2"))                                       | [2]
        department$.company$.info$.is(companyInfo$.address$.is(address$.address1$.isNull()))                          | [11, 12, 13, 41, 42]
        department$.company$.info$.is(companyInfo$.address$.is(address$.city$.eq("city_2")))                          | [2]
        department$.company$.is(company$.info$.address$.address1$.isNull())                                           | [11, 12, 13, 41, 42]
        department$.company$.is(company$.info$.address$.city$.eq("city_2"))                                           | [2]
        department$.company$.is(company$.info$.address$.is(address$.address1$.isNull()))                              | [11, 12, 13, 41, 42]
        department$.company$.is(company$.info$.address$.is(address$.city$.eq("city_2")))                              | [2]
        department$.company$.is(company$.info$.is(companyInfo$.address$.address1$.isNull()))                          | [11, 12, 13, 41, 42]
        department$.company$.is(company$.info$.is(companyInfo$.address$.city$.eq("city_2")))                          | [2]
        department$.company$.is(company$.info$.is(companyInfo$.address$.is(address$.address1$.isNull())))             | [11, 12, 13, 41, 42]
        department$.company$.is(company$.info$.is(companyInfo$.address$.is(address$.city$.eq("city_2"))))             | [2]
        _$.department$.company$.is(_$.company$.info$.is(_$.companyInfo$.address$.is(_$.address$.address1$.isNull()))) | [11, 12, 13, 41, 42]
        _$.department$.company$.is(_$.company$.info$.is(_$.companyInfo$.address$.is(_$.address$.city$.eq("city_2")))) | [2]
    }

    def "entity criteria inside embed criteria should filter entities to those that have not null association and match child criteria for embed object"() {
        //TODO: add this one
    }

    def "entity criteria inside multi-level embed criteria should filter entities to those that have not null association and match child criteria for hierarchy of embed objects"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                                                                                | expected
        company$.info$.address$.country$.code$.eq("by")                                                         | [4]
        company$.info$.address$.country$.name$.notNull()                                                        | [2]
        company$.info$.address$.country$.is(country$.code$.eq("by"))                                            | [4]
        company$.info$.address$.country$.is(country$.name$.notNull())                                           | [2]
        company$.info$.address$.is(address$.country$.code$.eq("by"))                                            | [4]
        company$.info$.address$.is(address$.country$.name$.notNull())                                           | [2]
        company$.info$.address$.is(address$.country$.is(country$.code$.eq("by")))                               | [4]
        company$.info$.address$.is(address$.country$.is(country$.name$.notNull()))                              | [2]
        company$.info$.is(companyInfo$.address$.country$.code$.eq("by"))                                        | [4]
        company$.info$.is(companyInfo$.address$.country$.name$.notNull())                                       | [2]
        company$.info$.is(companyInfo$.address$.country$.is(country$.code$.eq("by")))                           | [4]
        company$.info$.is(companyInfo$.address$.country$.is(country$.name$.notNull()))                          | [2]
        company$.info$.is(companyInfo$.address$.is(address$.country$.code$.eq("by")))                           | [4]
        company$.info$.is(companyInfo$.address$.is(address$.country$.name$.notNull()))                          | [2]
        company$.info$.is(companyInfo$.address$.is(address$.country$.is(country$.code$.eq("by"))))              | [4]
        company$.info$.is(companyInfo$.address$.is(address$.country$.is(country$.name$.notNull())))             | [2]
        _$.company$.info$.is(_$.companyInfo$.address$.is(_$.address$.country$.is(_$.country$.code$.eq("by"))))  | [4]
        _$.company$.info$.is(_$.companyInfo$.address$.is(_$.address$.country$.is(_$.country$.name$.notNull()))) | [2]
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
