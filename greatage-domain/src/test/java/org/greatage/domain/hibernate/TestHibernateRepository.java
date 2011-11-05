/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.greatage.domain.hibernate;

import org.dbunit.JdbcDatabaseTester;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.example.Company;
import org.example.Department;
import org.greatage.domain.EntityRepository;
import org.greatage.domain.Pagination;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import static org.example.criteria.Entities.company;
import static org.example.criteria.Entities.department;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestHibernateRepository {
	private HibernateExecutor executor;
	private EntityRepository repository;
	private JdbcDatabaseTester tester;

	@BeforeTest
	public void setup_repository() {
		final Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(Company.class);
		configuration.addAnnotatedClass(Department.class);
		final SessionFactory sessionFactory = configuration.buildSessionFactory();

		executor = new HibernateExecutorImpl(sessionFactory);
		repository = new HibernateRepository(executor, new HashMap<Class, Class>());
	}

	@BeforeTest(dependsOnMethods = "setup_repository")
	public void setup_database() throws Exception {
		final Properties properties = new Properties();
		properties.load(getClass().getResourceAsStream("/dbunit.properties"));
		tester = new JdbcDatabaseTester(
				properties.getProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS),
				properties.getProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL),
				properties.getProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME),
				properties.getProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD),
				properties.getProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA)
		);
		tester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		tester.setTearDownOperation(DatabaseOperation.DELETE_ALL);

		final InputStream inputStream = getClass().getResourceAsStream("/database.xml");
		final FlatXmlDataSet dataSet = new FlatXmlDataSetBuilder().build(inputStream);
		tester.setDataSet(dataSet);

		tester.onSetup();
	}

	@AfterTest
	public void cleanup_database() throws Exception {
		tester.onTearDown();
	}

	@Test
	public void select_company() {
		List<Company> companies = repository.find(Company.class, company.name.eq("company1"), Pagination.ALL);
		companies = repository.find(Company.class, company.id.eq(2l), Pagination.ALL);
		final List<Department> departments = repository.find(Department.class, department.name.eq("c1").and(department.company.name.eq("c1")), Pagination.ALL);
		System.out.println();

	}
}
