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

package org.greatage.domain.jpa;

import org.greatage.domain.AbstractEntityRepository;
import org.greatage.domain.Criteria;
import org.greatage.domain.Entity;
import org.greatage.domain.Pagination;
import org.greatage.util.DescriptionBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JpaRepository extends AbstractEntityRepository {
	private final JpaExecutor executor;

	public JpaRepository(final JpaExecutor executor, final Map<Class, Class> entityMapping) {
		super(entityMapping);
		this.executor = executor;
	}

	public <PK extends Serializable, E extends Entity<PK>>
	int count(final Class<E> entityClass, final Criteria<PK, E> criteria) {
		return execute("select count() from entityClass", entityClass, criteria, Pagination.ALL, new QueryCallback<Integer>() {
			public Integer doInQuery(final Query query) {
				return (Integer) query.getSingleResult();
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	List<E> find(final Class<E> entityClass, final Criteria<PK, E> criteria, final Pagination pagination) {
		return execute("select from entityClass", entityClass, criteria, pagination, new QueryCallback<List<E>>() {
			@SuppressWarnings({"unchecked"})
			public List<E> doInQuery(final Query query) {
				return query.getResultList();
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	List<PK> findKeys(final Class<E> entityClass, final Criteria<PK, E> criteria, final Pagination pagination) {
		throw new UnsupportedOperationException("cannot find keys");
	}

	public <PK extends Serializable, E extends Entity<PK>>
	List<Map<String, Object>> findValueObjects(final Class<E> entityClass, final Criteria<PK, E> criteria, final Map<String, String> projection, final Pagination pagination) {
		throw new UnsupportedOperationException("cannot find value objects");
	}

	public <PK extends Serializable, E extends Entity<PK>>
	E findUnique(final Class<E> entityClass, final Criteria<PK, E> criteria) {
		return execute("select from entityClass", entityClass, criteria, Pagination.UNIQUE, new QueryCallback<E>() {
			@SuppressWarnings({"unchecked"})
			public E doInQuery(final Query query) {
				return (E) query.getSingleResult();
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	E get(final Class<E> entityClass, final PK pk) {
		return executor.execute(new JpaCallback<E>() {
			public E doInJpa(final EntityManager em) throws Throwable {
				return em.find(getImplementation(entityClass), pk);
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void save(final E entity) {
		executor.execute(new JpaCallback<Object>() {
			public Object doInJpa(final EntityManager em) throws Throwable {
				em.persist(entity);
				return null;
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void update(final E entity) {
		executor.execute(new JpaCallback<Object>() {
			public Object doInJpa(final EntityManager em) throws Throwable {
				em.merge(entity);
				return null;
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void delete(final E entity) {
		executor.execute(new JpaCallback<Object>() {
			public Object doInJpa(final EntityManager em) throws Throwable {
				em.remove(entity);
				return null;
			}
		});
	}

	protected <T, PK extends Serializable, E extends Entity<PK>>
	T execute(final String queryString, final Class<E> entityClass, final Criteria<PK, E> criteria, final Pagination pagination, final QueryCallback<T> callback) {
		return executor.execute(new JpaCallback<T>() {
			public T doInJpa(EntityManager em) throws PersistenceException {
				final Query query = em.createQuery(queryString.replaceAll("entityClass", entityClass.getName()));

				if (pagination.getStart() > 0) {
					query.setFirstResult(pagination.getStart());
				}
				if (pagination.getCount() >= 0) {
					query.setMaxResults(pagination.getCount());
				}

				return callback.doInQuery(query);
			}
		});
	}

	public interface QueryCallback<T> {

		T doInQuery(Query query);

	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("executor", executor);
		return builder.toString();
	}
}
