package org.greatage.domain.hibernate

import org.example.model.Company
import org.greatage.domain.PropertyCriteriaSpecification
import spock.lang.Shared

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
class HibernatePropertyCriteriaSpec extends PropertyCriteriaSpecification {

    @Shared
    private HibernateTestData data = new HibernateTestData();

    def setupSpec() {
        repository = data.setup()
    }

    def cleanupSpec() {
        repository = data.cleanup()
    }

    //todo: move up
    def "like property criteria should find only entities with property value that matches specified pattern"() {
        when:
        def actual = findIds(entityClass, criteria)
        then:
        actual == expected

        where:
        entityClass   | criteria                            | expected
        Company.class | company$.name$.like("________")     | [1, 2, 3]
        Company.class | company$.name$.like("company")      | [5, 6]
        Company.class | company$.name$.like("company_")     | [1, 2, 3]
        Company.class | company$.name$.like("_company")     | []
        Company.class | company$.name$.like("_ompany_")     | [1, 2, 3]
        Company.class | company$.name$.like("%")            | [1, 2, 3, 5, 6]
        Company.class | company$.name$.like("%pany2")       | [2]
        Company.class | company$.name$.like("%company2")    | [2]
        Company.class | company$.name$.like("%pany")        | [5, 6]
        Company.class | company$.name$.like("company%")     | [1, 2, 3, 5, 6]
        Company.class | company$.name$.like("company2%")    | [2]
        Company.class | company$.name$.like("somecompany%") | []
        Company.class | company$.name$.like("%company%")    | [1, 2, 3, 5, 6]
        Company.class | company$.name$.like("%pany2%")      | [2]
        Company.class | company$.name$.like("%pany8%")      | []
        Company.class | company$.name$.like("%pany8%")      | []
        Company.class | company$.name$.like("%com%any%")    | [1, 2, 3, 5, 6]
    }
}
