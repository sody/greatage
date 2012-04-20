//package org.greatage.domain.hibernate;
//
//import org.dbunit.JdbcDatabaseTester;
//import org.dbunit.PropertiesBasedJdbcDatabaseTester;
//import org.dbunit.dataset.xml.FlatXmlDataSet;
//import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
//import org.dbunit.operation.DatabaseOperation;
//import org.example.hibernate.CompanyImpl;
//import org.example.hibernate.DepartmentImpl;
//import org.greatage.domain.Entity;
//import org.greatage.domain.EntityRepository;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//import org.testng.Assert;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//
//import java.io.InputStream;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Properties;
//
///**
// * @author Ivan Khalopik
// * @since 1.0
// */
//public abstract class AbstractHibernateRepositoryTest extends Assert {
//	private JdbcDatabaseTester tester;
//	protected EntityRepository repository;
//
//	@BeforeClass
//	public void setup_repository() throws Exception {
//		final Configuration configuration = new Configuration();
//		configuration.addAnnotatedClass(CompanyImpl.class);
//		configuration.addAnnotatedClass(DepartmentImpl.class);
//		final SessionFactory sessionFactory = configuration.buildSessionFactory();
//
//		final HibernateExecutor executor = new HibernateExecutor(sessionFactory);
//		repository = new HibernateRepository(executor, new HashMap<Class, Class>());
//
//		final Properties properties = new Properties();
//		properties.load(getClass().getResourceAsStream("/dbunit.properties"));
//		tester = new JdbcDatabaseTester(
//				properties.getProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS),
//				properties.getProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL),
//				properties.getProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME),
//				properties.getProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD),
//				properties.getProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA)
//		);
//		tester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
//		tester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
//
//		final InputStream inputStream = getClass().getResourceAsStream("/database.xml");
//		final FlatXmlDataSet dataSet = new FlatXmlDataSetBuilder().build(inputStream);
//		tester.setDataSet(dataSet);
//
//		tester.onSetup();
//	}
//
//	@AfterClass
//	public void cleanup_repository() throws Exception {
//		tester.onTearDown();
//		repository = null;
//	}
//
//	protected static <E extends Entity<Long>>
//	void assertEntities(final E actual, final E expected) {
//		assertEquals(actual, expected);
//		if (actual != null) {
//			if (actual instanceof CompanyImpl) {
//				assertCompanies((CompanyImpl) actual, (CompanyImpl) expected);
//			} else if (actual instanceof DepartmentImpl) {
//				assertDepartments((DepartmentImpl) actual, (DepartmentImpl) expected);
//			}
//		}
//	}
//
//	protected static void assertCompanies(final CompanyImpl actual, final CompanyImpl expected) {
//		assertEquals(actual.getId(), expected.getId());
//		assertEquals(actual.getName(), expected.getName());
//		assertEquals(actual.getRegisteredAt(), expected.getRegisteredAt());
//	}
//
//	protected static void assertDepartments(final DepartmentImpl actual, final DepartmentImpl expected) {
//		assertEquals(actual.getId(), expected.getId());
//		assertEquals(actual.getName(), expected.getName());
//		assertEntities(actual.getCompany(), expected.getCompany());
//	}
//
//	protected static <E extends Entity<Long>>
//	void assertIds(final List<E> actual, final long[] expected) {
//		final long[] actualIds = new long[actual.size()];
//		for (int i = 0; i < actualIds.length; i++) {
//			actualIds[i] = actual.get(i).getId();
//		}
//		final String message = String.format("Ids not match expected result:\n\texpected: %s\n\tactual: %s\n",
//				Arrays.toString(expected),
//				Arrays.toString(actualIds));
//		assertEquals(actualIds, expected, message);
//	}
//
//	protected static long[] ids(final long... ids) {
//		return ids;
//	}
//}
