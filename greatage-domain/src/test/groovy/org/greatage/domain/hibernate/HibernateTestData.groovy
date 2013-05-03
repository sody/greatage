package org.greatage.domain.hibernate

import org.dbunit.JdbcDatabaseTester
import org.dbunit.PropertiesBasedJdbcDatabaseTester
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder
import org.dbunit.operation.DatabaseOperation
import org.example.hibernate.CompanyImpl
import org.example.hibernate.CountryImpl
import org.example.hibernate.DepartmentImpl
import org.example.model.Company
import org.example.model.Country
import org.example.model.Department
import org.greatage.domain.Repository
import org.hibernate.cfg.Configuration

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
class HibernateTestData {

    private JdbcDatabaseTester tester;

    public Repository setup() {
        def configuration = new Configuration()
        configuration.addAnnotatedClass(CountryImpl.class)
        configuration.addAnnotatedClass(CompanyImpl.class)
        configuration.addAnnotatedClass(DepartmentImpl.class)
        def repository = new HibernateRepository(new HibernateSessionManager(configuration.buildSessionFactory()), [
                (Country.class): CountryImpl.class,
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

        return repository
    }

    public Repository cleanup() {
        tester.onTearDown()
        tester = null
        return null
    }
}
