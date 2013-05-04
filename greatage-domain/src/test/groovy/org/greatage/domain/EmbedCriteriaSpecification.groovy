package org.greatage.domain

import org.example.criteria._$
import org.example.model.Company
import spock.lang.Shared
import spock.lang.Specification

import static org.example.criteria.$.*

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
abstract class EmbedCriteriaSpecification extends Specification {

    @Shared
    protected Repository repository

    def "all criteria inside embed criteria should not filter entities"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                    | expected
        company$.info$.all()                        | [1, 2, 3, 4, 5, 6]
        company$.info$.is(companyInfo$.all())       | [1, 2, 3, 4, 5, 6]
        _$.company$.info$.is(_$.companyInfo$.all()) | [1, 2, 3, 4, 5, 6]
    }

    def "all criteria inside multiple levels of embed criteria should not filter entities"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                                             | expected
        company$.info$.address$.all()                                        | [1, 2, 3, 4, 5, 6]
        company$.info$.address$.is(address$.all())                           | [1, 2, 3, 4, 5, 6]
        company$.info$.is(companyInfo$.address$.all())                       | [1, 2, 3, 4, 5, 6]
        company$.info$.is(companyInfo$.address$.is(address$.all()))          | [1, 2, 3, 4, 5, 6]
        _$.company$.info$.is(_$.companyInfo$.address$.is(_$.address$.all())) | [1, 2, 3, 4, 5, 6]
    }

    def "property criteria inside embed criteria should filter entities to those that match property expression for embed object"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                                         | expected
        company$.info$.code$.eq("C2")                                    | [2]
        company$.info$.code$.eq("C8")                                    | []
        company$.info$.code$.isNull()                                    | [1, 5, 6]
        company$.info$.code$.in("C2", "C4", "C8")                        | [2, 4]
        company$.info$.is(companyInfo$.code$.eq("C2"))                   | [2]
        company$.info$.is(companyInfo$.code$.eq("C8"))                   | []
        company$.info$.is(companyInfo$.code$.isNull())                   | [1, 5, 6]
        company$.info$.is(companyInfo$.code$.in("C2", "C4", "C8"))       | [2, 4]
        _$.company$.info$.is(_$.companyInfo$.code$.eq("C2"))             | [2]
        _$.company$.info$.is(_$.companyInfo$.code$.eq("C8"))             | []
        _$.company$.info$.is(_$.companyInfo$.code$.isNull())             | [1, 5, 6]
        _$.company$.info$.is(_$.companyInfo$.code$.in("C2", "C4", "C8")) | [2, 4]
    }

    def "property criteria inside multiple levels of embed criteria should filter entities to those that match property expression for hierarchy of embed objects"() {
        when:
        def actual = findIds(Company.class, criteria)
        then:
        actual == expected

        where:
        criteria                                                                           | expected
        company$.info$.address$.address1$.notNull()                                        | [2, 3]
        company$.info$.address$.city$.eq("city_2")                                         | [2]
        company$.info$.address$.is(address$.address1$.notNull())                           | [2, 3]
        company$.info$.address$.is(address$.city$.eq("city_2"))                            | [2]
        company$.info$.is(companyInfo$.address$.address1$.notNull())                       | [2, 3]
        company$.info$.is(companyInfo$.address$.city$.eq("city_2"))                        | [2]
        company$.info$.is(companyInfo$.address$.is(address$.address1$.notNull()))          | [2, 3]
        company$.info$.is(companyInfo$.address$.is(address$.city$.eq("city_2")))           | [2]
        _$.company$.info$.is(_$.companyInfo$.address$.is(_$.address$.address1$.notNull())) | [2, 3]
        _$.company$.info$.is(_$.companyInfo$.address$.is(_$.address$.city$.eq("city_2")))  | [2]
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
