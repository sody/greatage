package org.greatage.domain

import org.dbunit.JdbcDatabaseTester
import org.dbunit.PropertiesBasedJdbcDatabaseTester
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder
import org.dbunit.operation.DatabaseOperation
import org.example.jdo.CompanyImpl
import org.example.jdo.DepartmentImpl
import org.example.model.Company
import org.example.model.Department
import org.greatage.domain.jdo.JDOExecutor
import org.greatage.domain.jdo.JDORepository
import spock.lang.Shared
import spock.lang.Specification

import javax.jdo.JDOHelper

import static org.example.model.Entities.company$

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
class JDORepositoryFindSpec extends Specification {

	@Shared
	private JdbcDatabaseTester tester;

	@Shared
	private EntityRepository repository;

	def setupSpec() {
		def factory = JDOHelper.getPersistenceManagerFactory("jdo.properties")
		repository = new JDORepository(new JDOExecutor(factory), [
				(Company.class): CompanyImpl.class,
				(Department.class): DepartmentImpl.class
		])
		repository.find(Company.class, Pagination.ALL)
		repository.find(Department.class, Pagination.ALL)

		def properties = new Properties()
		properties.load(getClass().getResourceAsStream("/dbunit.properties"))
		tester = new JdbcDatabaseTester(
				properties.getProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS),
				properties.getProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL),
				properties.getProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME),
				properties.getProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD),
				properties.getProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA)
		);
		tester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT)
		tester.setTearDownOperation(DatabaseOperation.DELETE_ALL)
		tester.setDataSet(new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream("/database.xml")))

		tester.onSetup()
	}

	def cleanupSpec() {
		tester.onTearDown()
		repository = null
	}

	def "all criteria should find all entities"() {
		when:
		def actual = toIds(repository.find(entityClass, criteria, Pagination.ALL))
		then:
		actual == expected

		where:
		entityClass   | criteria       | expected
		Company.class | company$.all() | [1, 2, 3, 4, 5, 6]
	}


	def "equal property criteria should find only entities with property value equal to specified"() {
		when:
		def actual = toIds(repository.find(entityClass, criteria, Pagination.ALL))
		then:
		actual == expected

		where:
		entityClass   | criteria                                         | expected
		Company.class | company$.id$.equal(1)                            | [1]
		Company.class | company$.id$.eq(4)                               | [4]
		Company.class | company$.id$.eq(10)                              | []
		Company.class | company$.id$.eq(null)                            | []
		Company.class | company$.name$.equal("company")                  | [5, 6]
		Company.class | company$.name$.eq("company1")                    | [1]
		Company.class | company$.name$.eq("company8")                    | []
		Company.class | company$.name$.eq(null)                          | [4]
		Company.class | company$.registeredAt$.equal(date("2001-01-01")) | [5]
		Company.class | company$.registeredAt$.eq(date("2001-01-02"))    | []
		Company.class | company$.registeredAt$.eq(null)                  | [1, 3, 4]
	}

	def "not equal property criteria should find only entities with property value not equal to specified"() {
		when:
		def actual = toIds(repository.find(entityClass, criteria, Pagination.ALL))
		then:
		actual == expected

		where:
		entityClass   | criteria                                      | expected
		Company.class | company$.id$.notEqual(1)                      | [2, 3, 4, 5, 6]
		Company.class | company$.id$.ne(10)                           | [1, 2, 3, 4, 5, 6]
		Company.class | company$.id$.ne(null)                         | [1, 2, 3, 4, 5, 6]
		Company.class | company$.name$.notEqual("company")            | [1, 2, 3, 4]
		Company.class | company$.name$.ne("company1")                 | [2, 3, 4, 5, 6]
		Company.class | company$.name$.ne("company8")                 | [1, 2, 3, 4, 5, 6]
		Company.class | company$.name$.ne(null)                       | [1, 2, 3, 5, 6]
		Company.class | company$.registeredAt$.ne(date("2001-01-01")) | [1, 2, 3, 4, 6]
		Company.class | company$.registeredAt$.ne(date("2001-01-02")) | [1, 2, 3, 4, 5, 6]
		Company.class | company$.registeredAt$.notEqual(null)         | [2, 5, 6]
	}

	def "is null property criteria should find only entities with property value equal to null"() {
		when:
		def actual = toIds(repository.find(entityClass, criteria, Pagination.ALL))
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

	def "is not null property criteria should find only entities with property value not equal to null"() {
		when:
		def actual = toIds(repository.find(entityClass, criteria, Pagination.ALL))
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

	def "greater than property criteria should find only entities with property value greater than specified"() {
		when:
		def actual = toIds(repository.find(entityClass, criteria, Pagination.ALL))
		then:
		actual == expected

		where:
		entityClass   | criteria                                               | expected
		Company.class | company$.id$.greaterThan(4)                            | [5, 6]
		Company.class | company$.id$.gt(6)                                     | []
		Company.class | company$.id$.gt(10)                                    | []
		Company.class | company$.id$.gt(null)                                  | [1, 2, 3, 4, 5, 6]
		Company.class | company$.name$.greaterThan("company")                  | [1, 2, 3]
		Company.class | company$.name$.gt("a")                                 | [1, 2, 3, 5, 6]
		Company.class | company$.name$.gt("z")                                 | []
		Company.class | company$.name$.gt(null)                                | [1, 2, 3, 5, 6]
		Company.class | company$.registeredAt$.greaterThan(date("2001-01-01")) | [2, 6]
		Company.class | company$.registeredAt$.gt(date("2010-10-10"))          | []
		Company.class | company$.registeredAt$.gt(date("2012-10-10"))          | []
		Company.class | company$.registeredAt$.gt(null)                        | [2, 5, 6]
	}

	def "greater or equal property criteria should find only entities with property value greater or equal to specified"() {
		when:
		def actual = toIds(repository.find(entityClass, criteria, Pagination.ALL))
		then:
		actual == expected

		where:
		entityClass   | criteria                                                  | expected
		Company.class | company$.id$.greaterOrEqual(4)                            | [4, 5, 6]
		Company.class | company$.id$.ge(6)                                        | [6]
		Company.class | company$.id$.ge(10)                                       | []
		Company.class | company$.id$.ge(null)                                     | [1, 2, 3, 4, 5, 6]
		Company.class | company$.name$.greaterOrEqual("company")                  | [1, 2, 3, 5, 6]
		Company.class | company$.name$.ge("a")                                    | [1, 2, 3, 5, 6]
		Company.class | company$.name$.ge("z")                                    | []
		Company.class | company$.name$.ge(null)                                   | [1, 2, 3, 4, 5, 6]
		Company.class | company$.registeredAt$.greaterOrEqual(date("2001-01-01")) | [2, 5, 6]
		Company.class | company$.registeredAt$.ge(date("2010-10-10"))             | [2]
		Company.class | company$.registeredAt$.ge(date("2012-10-10"))             | []
		Company.class | company$.registeredAt$.ge(null)                           | [1, 2, 3, 4, 5, 6]
	}

	def "less than property criteria should find only entities with property value less than specified"() {
		when:
		def actual = toIds(repository.find(entityClass, criteria, Pagination.ALL))
		then:
		actual == expected

		where:
		entityClass   | criteria                                            | expected
		Company.class | company$.id$.lessThan(4)                            | [1, 2, 3]
		Company.class | company$.id$.lt(1)                                  | []
		Company.class | company$.id$.lt(10)                                 | [1, 2, 3, 4, 5, 6]
		Company.class | company$.id$.lt(null)                               | []
		Company.class | company$.name$.lessThan("company")                  | [4]
		Company.class | company$.name$.lt("a")                              | [4]
		Company.class | company$.name$.lt("z")                              | [1, 2, 3, 4, 5, 6]
		Company.class | company$.name$.lt(null)                             | []
		Company.class | company$.registeredAt$.lessThan(date("2001-01-01")) | [1, 3, 4]
		Company.class | company$.registeredAt$.lt(date("2010-10-10"))       | [1, 3, 4, 5, 6]
		Company.class | company$.registeredAt$.lt(date("2012-10-10"))       | [1, 2, 3, 4, 5, 6]
		Company.class | company$.registeredAt$.lt(null)                     | []
	}

	def "less or equal property criteria should find only entities with property value less or equal to specified"() {
		when:
		def actual = toIds(repository.find(entityClass, criteria, Pagination.ALL))
		then:
		actual == expected

		where:
		entityClass   | criteria                                               | expected
		Company.class | company$.id$.lessOrEqual(4)                            | [1, 2, 3, 4]
		Company.class | company$.id$.le(1)                                     | [1]
		Company.class | company$.id$.le(10)                                    | [1, 2, 3, 4, 5, 6]
		Company.class | company$.id$.le(null)                                  | []
		Company.class | company$.name$.lessOrEqual("company")                  | [4, 5, 6]
		Company.class | company$.name$.le("a")                                 | [4]
		Company.class | company$.name$.le("z")                                 | [1, 2, 3, 4, 5, 6]
		Company.class | company$.name$.le(null)                                | [4]
		Company.class | company$.registeredAt$.lessOrEqual(date("2001-01-01")) | [1, 3, 4, 5]
		Company.class | company$.registeredAt$.le(date("2010-10-10"))          | [1, 2, 3, 4, 5, 6]
		Company.class | company$.registeredAt$.le(date("2012-10-10"))          | [1, 2, 3, 4, 5, 6]
		Company.class | company$.registeredAt$.le(null)                        | [1, 3, 4]
	}

	def "in property criteria should find only entities with property value in specified range"() {
		when:
		def actual = toIds(repository.find(entityClass, criteria, Pagination.ALL))
		then:
		actual == expected

		where:
		entityClass   | criteria                                                                              | expected
		Company.class | company$.id$.in(1, 2, 3)                                                              | [1, 2, 3]
		Company.class | company$.id$.in(1)                                                                    | [1]
		Company.class | company$.id$.in(10)                                                                   | []
		Company.class | company$.id$.in(1, 6, 10)                                                             | [1, 6]
		Company.class | company$.id$.in([null])                                                               | []
		Company.class | company$.id$.in(1, null, 10)                                                          | [1]
		Company.class | company$.name$.in("company", "company1")                                              | [1, 5, 6]
		Company.class | company$.name$.in("company")                                                          | [5, 6]
		Company.class | company$.name$.in("company8")                                                         | []
		Company.class | company$.name$.in("company2", "company8")                                             | [2]
		Company.class | company$.name$.in([null])                                                             | [4]
		Company.class | company$.name$.in("company2", "company8", null)                                       | [2, 4]
		Company.class | company$.registeredAt$.in(date("2001-01-01"), date("2001-02-02"))                     | [5, 6]
		Company.class | company$.registeredAt$.in(date("2012-10-10"))                                         | []
		Company.class | company$.registeredAt$.in(date("2001-01-01"), date("2010-10-10"), date("1999-01-01")) | [2, 5]
		Company.class | company$.registeredAt$.in([null])                                                     | [1, 3, 4]
		Company.class | company$.registeredAt$.in(date("2001-01-01"), null)                                   | [1, 3, 4, 5]
	}

	def "like property criteria should find only entities with property value like specified"() {
		//todo:
	}

	private Date date(final String input) {
		return Date.parse("yyyy-MM-dd", input)
	}

	private List<Long> toIds(final List<? extends Entity<Long>> entities) {
		def result = new ArrayList<Long>()
		for (Entity<Long> entity : entities) {
			result.add(entity.id)
		}
		Collections.sort(result)
		return result
	}
}
