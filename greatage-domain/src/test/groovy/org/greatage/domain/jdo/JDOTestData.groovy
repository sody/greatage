package org.greatage.domain.jdo

import org.dbunit.JdbcDatabaseTester
import org.dbunit.PropertiesBasedJdbcDatabaseTester
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder
import org.dbunit.operation.DatabaseOperation
import org.example.model.Company
import org.example.model.Department
import org.greatage.domain.Repository
import javax.jdo.JDOHelper
import org.example.jdo.CompanyImpl
import org.example.jdo.DepartmentImpl

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
class JDOTestData {

	private JdbcDatabaseTester tester;

	public Repository setup() {
		def factory = JDOHelper.getPersistenceManagerFactory("jdo.properties")
		def repository = new JDORepository(new JDOExecutor(factory), [
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

		return repository
	}

	public Repository cleanup() {
		tester.onTearDown()
		tester = null
		return null
	}
}
