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

import javax.jdo.JDOHelper

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
class JDORepositoryFindSpec extends PropertyCriteriaSpecification {

	@Shared
	private JdbcDatabaseTester tester;

	def setupSpec() {
		def factory = JDOHelper.getPersistenceManagerFactory("jdo.properties")
		repository = new JDORepository(new JDOExecutor(factory), [
				(Company.class): CompanyImpl.class,
				(Department.class): DepartmentImpl.class
		])
		repository.query(Company.class).list()
		repository.query(Department.class).list()

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
}
