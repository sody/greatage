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

import org.example.hibernate.Company;
import org.example.hibernate.Department;
import org.greatage.domain.Criteria;
import org.greatage.domain.Entity;
import org.greatage.domain.Pagination;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static org.example.hibernate.Entities.company;
import static org.example.hibernate.Entities.department;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestHibernateRepository extends AbstractHibernateRepositoryTest {

	@DataProvider
	public Object[][] find_data() {
		return new Object[][]{
				{Company.class, company.all(), ids(1, 2, 3, 4)},

				{Company.class, company.id.eq(3l), ids(3)},
				{Company.class, company.id.eq(8l), ids()},
				{Company.class, company.name.eq("company2"), ids(2)},
				{Company.class, company.name.eq("c1"), ids()},

				{Company.class, company.id.ne(3l), ids(1, 2, 4)},
				{Company.class, company.id.ne(8l), ids(1, 2, 3, 4)},
				// null is ignored
				{Company.class, company.name.ne("company2"), ids(1, 3)},
				{Company.class, company.name.ne("c1"), ids(1, 2, 3)},

				{Company.class, company.id.isNull(), ids()},
				{Company.class, company.id.notNull(), ids(1, 2, 3, 4)},
				{Company.class, company.name.isNull(), ids(4)},
				{Company.class, company.name.notNull(), ids(1, 2, 3)},

				{Company.class, company.id.gt(3l), ids(4)},
				{Company.class, company.id.gt(8l), ids()},
				{Company.class, company.name.gt("company2"), ids(3)},
				{Company.class, company.name.gt("c1"), ids(1, 2, 3)},

				{Company.class, company.id.ge(3l), ids(3, 4)},
				{Company.class, company.id.ge(8l), ids()},
				{Company.class, company.name.ge("company2"), ids(2, 3)},
				{Company.class, company.name.ge("c1"), ids(1, 2, 3)},

				{Company.class, company.id.lt(3l), ids(1, 2)},
				{Company.class, company.id.lt(8l), ids(1, 2, 3, 4)},
				{Company.class, company.name.lt("company2"), ids(1)},
				{Company.class, company.name.lt("c1"), ids()},

				{Company.class, company.id.le(3l), ids(1, 2, 3)},
				{Company.class, company.id.le(8l), ids(1, 2, 3, 4)},
				{Company.class, company.name.le("company2"), ids(1, 2)},
				{Company.class, company.name.le("c1"), ids()},

				{Company.class, company.id.like(3l), ids(3)},
				{Company.class, company.id.like(8l), ids()},
				{Company.class, company.name.like("company2"), ids(2)},
				{Company.class, company.name.like("c1"), ids()},

				{Company.class, company.name.like("%pany2"), ids(2)},
				{Company.class, company.name.like("%company2"), ids(2)},
				{Company.class, company.name.like("%pany"), ids()},
				{Company.class, company.name.like("company%"), ids(1, 2, 3)},
				{Company.class, company.name.like("company2%"), ids(2)},
				{Company.class, company.name.like("somecompany%"), ids()},
				{Company.class, company.name.like("%company%"), ids(1, 2, 3)},
				{Company.class, company.name.like("%pany2%"), ids(2)},
				{Company.class, company.name.like("%pany8%"), ids()},
				{Company.class, company.name.like("%pany8%"), ids()},
				{Company.class, company.name.like("%com%any%"), ids(1, 2, 3)},

				{Company.class, company.id.in(1l, 3l), ids(1, 3)},
				{Company.class, company.id.in(1l, 8l), ids(1)},
				{Company.class, company.id.in(7l, 8l), ids()},
				{Company.class, company.name.in("company2", "company3"), ids(2, 3)},
				{Company.class, company.name.in("company8", "company3"), ids(3)},
				{Company.class, company.name.in("c1"), ids()},

				{Company.class, company.name.eq("company1").and(company.id.eq(1l)), ids(1)},
				{Company.class, company.name.eq("company1").and(company.id.eq(2l)), ids()},
				{Company.class, company.name.eq("company1").or(company.id.eq(1l)), ids(1)},
				{Company.class, company.name.eq("company1").or(company.id.eq(2l)), ids(1, 2)},
				{Company.class, company.name.like("company%").or(company.name.isNull()), ids(1, 2, 3, 4)},

				{Company.class, company.name.eq("company2").not(), ids(1, 3)},
				{Company.class, company.name.eq("company2").not().not(), ids(2)},
				{Company.class, company.id.gt(3l).not(), ids(1, 2, 3)},
				{Company.class, company.id.gt(3l).not().not(), ids(4)},
				{Company.class, company.name.eq("company1").and(company.id.eq(1l)).not(), ids(2, 3)},
				{Company.class, company.id.eq(1l).or(company.id.eq(2l)).not(), ids(3, 4)},

				{Company.class, company.name.eq("company1").and(company.id.eq(1l)).and(company.name.like("company%")), ids(1)},
				{Company.class, company.name.eq("company1").and(company.id.eq(2l)).and(company.name.isNull()), ids()},
				{Company.class, company.name.eq("company1").or(company.id.eq(1l)).or(company.name.like("company%")), ids(1, 2, 3)},
				{Company.class, company.name.eq("company1").or(company.id.eq(2l)).or(company.name.isNull()), ids(1, 2, 4)},
				{Company.class, company.name.eq("company1").and(company.id.eq(1l)).or(company.name.like("company%")), ids(1, 2, 3)},
				{Company.class, company.name.eq("company1").or(company.id.eq(4l)).and(company.name.isNull()), ids(4)},

				{Department.class, department.name.like("dep%").and(department.company.name.eq("company1")), ids(1, 2, 3)},
				{Department.class, department.name.eq("department4").and(department.company.name.eq("company1")), ids()},
		};
	}

	@Test(dataProvider = "find_data")
	public <E extends Entity<Long>>
	void test_find(final Class<E> entityClass, final Criteria<Long, E> criteria, final long[] expectedIds) {
		final List<E> actual = repository.find(entityClass, criteria, Pagination.ALL);
		assertNotNull(actual);
		assertIds(actual, expectedIds);
	}

	@Test(dataProvider = "find_data")
	public <E extends Entity<Long>>
	void test_count(final Class<E> entityClass, final Criteria<Long, E> criteria, final long[] expectedIds) {
		final long actualCount = repository.count(entityClass, criteria);
		assertEquals(actualCount, expectedIds.length);
	}

	@DataProvider
	public Object[][] find_all_data() {
		return new Object[][]{
				{Company.class, ids(1, 2, 3, 4)},
				{Department.class, ids(1, 2, 3, 4, 5)},
		};
	}

	@Test(dataProvider = "find_all_data")
	public <E extends Entity<Long>>
	void test_find_all(final Class<E> entityClass, final long[] expectedIds) {
		final List<E> actual = repository.find(entityClass, Pagination.ALL);
		assertNotNull(actual);
		assertIds(actual, expectedIds);
	}

	@Test(dataProvider = "find_all_data")
	public <E extends Entity<Long>>
	void test_count_all(final Class<E> entityClass, final long[] expectedIds) {
		final long actualCount = repository.count(entityClass);
		assertEquals(actualCount, expectedIds.length);
	}

	@DataProvider
	public Object[][] sorting_data() {
		return new Object[][]{
				{Company.class, company.id.asc(), ids(1, 2, 3, 4)},
				{Company.class, company.id.desc(), ids(4, 3, 2, 1)},
				{Company.class, company.name.sort(true), ids(4, 1, 2, 3)},
				{Company.class, company.name.sort(false), ids(3, 2, 1, 4)},

				{Company.class, company.name.like("company%").and(company.name.asc()), ids(1, 2, 3)},
				{Department.class,
						department.name.like("dep%")
								.and(department.company.name.eq("company1"))
								.and(department.name.desc()),
						ids(3, 2, 1)},
				{Department.class,
						department.name.like("dep%")
								.and(department.company.name.like("company%"))
								.and(department.company.name.asc())
								.and(department.name.desc()),
						ids(3, 2, 1, 5, 4)},
		};
	}

	@Test(dataProvider = "sorting_data")
	public <E extends Entity<Long>>
	void test_sorting(final Class<E> entityClass, final Criteria<Long, E> criteria, final long[] expectedIds) {
		final List<E> actual = repository.find(entityClass, criteria, Pagination.ALL);
		assertNotNull(actual);
		assertIds(actual, expectedIds);
	}
}
