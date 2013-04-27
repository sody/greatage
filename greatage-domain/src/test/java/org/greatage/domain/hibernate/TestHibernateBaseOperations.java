//package org.greatage.domain.hibernate;
//
//import org.example.hibernate.CompanyImpl;
//import org.example.hibernate.DepartmentImpl;
//import org.greatage.domain.Entity;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
//
//import java.util.Date;
//
///**
// * @author Ivan Khalopik
// * @since 1.0
// */
//public class TestHibernateBaseOperations extends AbstractHibernateRepositoryTest {
//
//	@DataProvider
//	public Object[][] get_data() {
//		final CompanyImpl company1 = new CompanyImpl(1l, "company1");
//		final CompanyImpl company3 = new CompanyImpl(3l, "company3");
//		final CompanyImpl company2 = new CompanyImpl(2l, "company2");
//		final CompanyImpl company4 = new CompanyImpl(4l, null);
//
//		return new Object[][]{
//				{CompanyImpl.class, 1l, company1},
//				{CompanyImpl.class, 2l, company2},
//				{CompanyImpl.class, 3l, company3},
//				{CompanyImpl.class, 4l, company4},
//				{CompanyImpl.class, 5l, null},
//
//				{DepartmentImpl.class, 1l, new DepartmentImpl(1l, "department1", company1)},
//				{DepartmentImpl.class, 2l, new DepartmentImpl(2l, "department2", company1)},
//				{DepartmentImpl.class, 3l, new DepartmentImpl(3l, "department3", company1)},
//				{DepartmentImpl.class, 4l, new DepartmentImpl(4l, "department4", company3)},
//				{DepartmentImpl.class, 5l, new DepartmentImpl(5l, "department5", company3)},
//				{DepartmentImpl.class, 6l, null},
//		};
//	}
//
//	@Test(dataProvider = "get_data")
//	public <E extends Entity<Long>>
//	void test_get(final Class<E> entityClass, final long id, final E expected) {
//		final E actual = repository.get(entityClass, id);
//		assertEntities(actual, expected);
//	}
//
//	@DataProvider
//	public Object[][] crud_data() {
//		return new Object[][]{
//				{CompanyImpl.class, new CompanyImpl(1000l, "company1"), new CompanyImpl(1000l, "company2")},
//				{CompanyImpl.class, new CompanyImpl(1000l, null), new CompanyImpl(1000l, "company2")},
//				{CompanyImpl.class, new CompanyImpl(1000l, "company1"), new CompanyImpl(1000l, null)},
//				{CompanyImpl.class, new CompanyImpl(1000l, null, new Date()), new CompanyImpl(1000l, "company2", new Date())},
//				{CompanyImpl.class, new CompanyImpl(1000l, null), new CompanyImpl(1000l, "company2", new Date())},
//
//				{DepartmentImpl.class, new DepartmentImpl(1000l, "department1"), new DepartmentImpl(1000l, null, new CompanyImpl(1l, "company1"))},
//				{DepartmentImpl.class, new DepartmentImpl(1000l, "department1", new CompanyImpl(1l, "company1")), new DepartmentImpl(1000l, null)},
//				{DepartmentImpl.class, new DepartmentImpl(1000l, "department1", new CompanyImpl(1l, "company1")), new DepartmentImpl(1000l, null, new CompanyImpl(2l, "company2"))},
//		};
//	}
//
//	@Test(dataProvider = "crud_data")
//	public <E extends Entity<Long>>
//	void test_crud(final Class<E> entityClass, final E createEntity, final E updateEntity) {
//		assertEquals(createEntity.getId(), updateEntity.getId());
//		final Long id = createEntity.getId();
//
//		E actual = repository.get(entityClass, id);
//		assertNull(actual);
//
//		repository.insert(createEntity);
//		actual = repository.get(entityClass, id);
//		assertNotNull(actual);
//		assertEntities(actual, createEntity);
//
//		repository.update(updateEntity);
//		actual = repository.get(entityClass, id);
//		assertNotNull(actual);
//		assertEntities(actual, updateEntity);
//
//		repository.remove(updateEntity);
//		actual = repository.get(entityClass, id);
//		assertNull(actual);
//	}
//}
