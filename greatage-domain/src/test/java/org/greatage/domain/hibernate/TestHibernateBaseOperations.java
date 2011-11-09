package org.greatage.domain.hibernate;

import org.example.hibernate.Company;
import org.example.hibernate.Department;
import org.greatage.domain.Entity;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Date;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestHibernateBaseOperations extends AbstractHibernateRepositoryTest {

	@DataProvider
	public Object[][] get_data() {
		final Company company1 = new Company(1l, "company1");
		final Company company3 = new Company(3l, "company3");
		final Company company2 = new Company(2l, "company2");
		final Company company4 = new Company(4l, null);

		return new Object[][]{
				{Company.class, 1l, company1},
				{Company.class, 2l, company2},
				{Company.class, 3l, company3},
				{Company.class, 4l, company4},
				{Company.class, 5l, null},

				{Department.class, 1l, new Department(1l, "department1", company1)},
				{Department.class, 2l, new Department(2l, "department2", company1)},
				{Department.class, 3l, new Department(3l, "department3", company1)},
				{Department.class, 4l, new Department(4l, "department4", company3)},
				{Department.class, 5l, new Department(5l, "department5", company3)},
				{Department.class, 6l, null},
		};
	}

	@Test(dataProvider = "get_data")
	public <E extends Entity<Long>>
	void test_get(final Class<E> entityClass, final long id, final E expected) {
		final E actual = repository.get(entityClass, id);
		assertEntities(actual, expected);
	}

	@DataProvider
	public Object[][] crud_data() {
		return new Object[][]{
				{Company.class, new Company(1000l, "company1"), new Company(1000l, "company2")},
				{Company.class, new Company(1000l, null), new Company(1000l, "company2")},
				{Company.class, new Company(1000l, "company1"), new Company(1000l, null)},
				{Company.class, new Company(1000l, null, new Date()), new Company(1000l, "company2", new Date())},
				{Company.class, new Company(1000l, null), new Company(1000l, "company2", new Date())},

				{Department.class, new Department(1000l, "department1"), new Department(1000l, null, new Company(1l, "company1"))},
				{Department.class, new Department(1000l, "department1", new Company(1l, "company1")), new Department(1000l, null)},
				{Department.class, new Department(1000l, "department1", new Company(1l, "company1")), new Department(1000l, null, new Company(2l, "company2"))},
		};
	}

	@Test(dataProvider = "crud_data")
	public <E extends Entity<Long>>
	void test_crud(final Class<E> entityClass, final E createEntity, final E updateEntity) {
		assertEquals(createEntity.getId(), updateEntity.getId());
		final Long id = createEntity.getId();

		E actual = repository.get(entityClass, id);
		assertNull(actual);

		repository.save(createEntity);
		actual = repository.get(entityClass, id);
		assertNotNull(actual);
		assertEntities(actual, createEntity);

		repository.update(updateEntity);
		actual = repository.get(entityClass, id);
		assertNotNull(actual);
		assertEntities(actual, updateEntity);

		repository.delete(updateEntity);
		actual = repository.get(entityClass, id);
		assertNull(actual);
	}
}
