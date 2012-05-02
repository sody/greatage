package org.greatage.domain

import org.dbunit.JdbcDatabaseTester
import org.dbunit.PropertiesBasedJdbcDatabaseTester
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder
import org.dbunit.operation.DatabaseOperation
import org.example.hibernate.CompanyImpl
import org.example.hibernate.DepartmentImpl
import org.example.model.Company
import org.example.model.Department
import org.greatage.domain.hibernate.HibernateExecutor
import org.greatage.domain.hibernate.HibernateRepository
import org.hibernate.cfg.Configuration
import spock.lang.Shared

import static org.example.model.Entities.company$

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
class HibernateRepositoryFindSpec extends PropertyCriteriaSpecification {

	@Shared
	private JdbcDatabaseTester tester;

	def setupSpec() {
		def configuration = new Configuration()
		configuration.addAnnotatedClass(CompanyImpl.class)
		configuration.addAnnotatedClass(DepartmentImpl.class)
		repository = new HibernateRepository(new HibernateExecutor(configuration.buildSessionFactory()), [
				(Company.class): CompanyImpl.class,
				(Department.class): DepartmentImpl.class
		])

		def properties = new Properties();
		properties.load(getClass().getResourceAsStream("/dbunit.properties"))
		tester = new JdbcDatabaseTester(
				properties.getProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS),
				properties.getProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL),
				properties.getProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME),
				properties.getProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD),
				properties.getProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA)
		)
		tester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT)
		tester.setTearDownOperation(DatabaseOperation.DELETE_ALL)
		tester.setDataSet(new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream("/database.xml")))

		tester.onSetup()
	}

	def cleanupSpec() {
		tester.onTearDown()
		repository = null
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
