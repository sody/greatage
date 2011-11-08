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

package org.greatage.domain.jdo;

import org.greatage.domain.AbstractEntityRepository;
import org.greatage.domain.Criteria;
import org.greatage.domain.Entity;
import org.greatage.domain.Pagination;
import org.greatage.util.DescriptionBuilder;

import javax.jdo.Extent;
import javax.jdo.JDOException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JDORepository1 extends AbstractEntityRepository {
	private static final String COUNT_RESULT = "count(id)";

	private final JDOExecutor1 executor;

	public JDORepository1(final JDOExecutor1 executor, final Map<Class, Class> entityMapping) {
		super(entityMapping);
		this.executor = executor;
	}

	public <PK extends Serializable, E extends Entity<PK>>
	long count(final Class<E> entityClass, final Criteria<PK, E> criteria) {
		return execute(entityClass, criteria, Pagination.ALL, new QueryCallback<Number>() {
			public Number doInQuery(final Query query, final Map parameters) {
				query.setResult(COUNT_RESULT);
				query.setUnique(true);
				return (Number) query.executeWithMap(parameters);
			}
		}).longValue();
	}

	public <PK extends Serializable, E extends Entity<PK>>
	List<E> find(final Class<E> entityClass, final Criteria<PK, E> criteria, final Pagination pagination) {
		return execute(entityClass, criteria, pagination, new QueryCallback<List<E>>() {
			@SuppressWarnings({"unchecked"})
			public List<E> doInQuery(final Query query, final Map parameters) {
				return (List<E>) query.executeWithMap(parameters);
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	List<PK> findKeys(final Class<E> entityClass, final Criteria<PK, E> criteria, final Pagination pagination) {
		//todo: implement this
		throw new UnsupportedOperationException("cannot find keys");
	}

	public <PK extends Serializable, E extends Entity<PK>>
	List<Map<String, Object>> findValueObjects(final Class<E> entityClass, final Criteria<PK, E> criteria, final Map<String, String> projection, final Pagination pagination) {
		//todo: implement this
		throw new UnsupportedOperationException("cannot find value objects");
	}

	public <PK extends Serializable, E extends Entity<PK>>
	E findUnique(final Class<E> entityClass, final Criteria<PK, E> criteria) {
		return execute(entityClass, criteria, Pagination.UNIQUE, new QueryCallback<E>() {
			@SuppressWarnings({"unchecked"})
			public E doInQuery(final Query query, final Map parameters) {
				query.setUnique(true);
				return (E) query.executeWithMap(parameters);
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	E get(final Class<E> entityClass, final PK pk) {
		return executor.execute(new JDOCallback1<E>() {
			public E doInJdo(final PersistenceManager pm) throws Throwable {
				try {
					return pm.getObjectById(getImplementation(entityClass), pk);
				} catch (Exception e) {
					// todo: needs to process only needed exceptions
					return null;
				}
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void save(final E entity) {
		executor.execute(new JDOCallback1<Object>() {
			public Object doInJdo(final PersistenceManager pm) throws Throwable {
				pm.makePersistent(entity);
				return null;
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void update(final E entity) {
		executor.execute(new JDOCallback1<Object>() {
			public Object doInJdo(final PersistenceManager pm) throws Throwable {
				pm.refresh(entity);
				return null;
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void delete(final E entity) {
		executor.execute(new JDOCallback1<Object>() {
			public Object doInJdo(final PersistenceManager pm) throws Throwable {
				pm.deletePersistent(entity);
				return null;
			}
		});
	}

	private <T, PK extends Serializable, E extends Entity<PK>>
	T execute(final Class<E> entityClass, final Criteria<PK, E> criteria, final Pagination pagination, final QueryCallback<T> callback) {
		return executor.execute(new JDOCallback1<T>() {
			public T doInJdo(final PersistenceManager pm) throws JDOException {
				final Extent<? extends Entity> extent = pm.getExtent(getImplementation(entityClass), true);
				final Query query = pm.newQuery(extent);

				final JDOCriteriaVisitor<PK, E> visitor = new JDOCriteriaVisitor<PK, E>(query);
				visitor.visit(criteria);

				if (pagination.getCount() >= 0) {
					final int start = pagination.getStart() > 0 ? pagination.getStart() : 0;
					final int end = start + pagination.getCount();
					query.setRange(start, end);
				}

				return callback.doInQuery(query, visitor.getParameters());
			}
		});
	}

	public static interface QueryCallback<T> {

		T doInQuery(Query query, Map parameters);
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("executor", executor);
		return builder.toString();
	}
}
