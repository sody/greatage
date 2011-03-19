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

import org.greatage.domain.*;
import org.greatage.util.DescriptionBuilder;

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
public class JdoRepository extends AbstractEntityRepository {
	private static final String COUNT_RESULT = "count()";

	private final JdoExecutor executor;
	private final EntityFilterProcessor filterProcessor;

	public JdoRepository(final Map<Class, Class> entityMapping,
						 final JdoExecutor executor,
						 final EntityFilterProcessor filterProcessor) {
		super(entityMapping);
		this.executor = executor;
		this.filterProcessor = filterProcessor;
	}

	public <PK extends Serializable, E extends Entity<PK>>
	int count(final EntityFilter<PK, E> filter) {
		return execute(filter, Pagination.ALL, new QueryCallback<Integer>() {
			public Integer doInQuery(final Query query) {
				query.setResult(COUNT_RESULT);
				return (Integer) query.execute();
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	List<E> find(final EntityFilter<PK, E> filter, final Pagination pagination) {
		return execute(filter, pagination, new QueryCallback<List<E>>() {
			@SuppressWarnings({"unchecked"})
			public List<E> doInQuery(final Query query) {
				return (List<E>) query.execute();
			}
		});
	}

	//todo: implement this

	public <PK extends Serializable, E extends Entity<PK>>
	List<PK> findKeys(final EntityFilter<PK, E> filter, final Pagination pagination) {
		throw new UnsupportedOperationException("cannot find keys");
	}

	//todo: implement this

	public <PK extends Serializable, E extends Entity<PK>>
	List<Map<String, Object>> findValueObjects(final EntityFilter<PK, E> filter, final Map<String, String> projection, final Pagination pagination) {
		throw new UnsupportedOperationException("cannot find value objects");
	}

	//todo: not tested

	public <PK extends Serializable, E extends Entity<PK>>
	E findUnique(final EntityFilter<PK, E> filter) {
		return execute(filter, Pagination.UNIQUE, new QueryCallback<E>() {
			@SuppressWarnings({"unchecked"})
			public E doInQuery(final Query query) {
				query.setUnique(true);
				return (E) query.execute();
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	E get(final Class<E> entityClass, final PK pk) {
		return executor.execute(new JdoCallback<E>() {
			public E doInJdo(final PersistenceManager pm) throws Throwable {
				return pm.getObjectById(getImplementation(entityClass), pk);
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void save(final E entity) {
		executor.execute(new JdoCallback<Object>() {
			public Object doInJdo(final PersistenceManager pm) throws Throwable {
				pm.makePersistent(entity);
				return null;
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void update(final E entity) {
		executor.execute(new JdoCallback<Object>() {
			public Object doInJdo(final PersistenceManager pm) throws Throwable {
				pm.refresh(entity);
				return null;
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void delete(final E entity) {
		executor.execute(new JdoCallback<Object>() {
			public Object doInJdo(final PersistenceManager pm) throws Throwable {
				pm.deletePersistent(entity);
				return null;
			}
		});
	}

	private <T, PK extends Serializable, E extends Entity<PK>>
	T execute(final EntityFilter<PK, E> filter, final Pagination pagination, final QueryCallback<T> callback) {
		return executor.execute(new JdoCallback<T>() {
			public T doInJdo(final PersistenceManager pm) throws JDOException {
				final JdoCriteria criteria = JdoCriteria.forClass(pm, getImplementation(filter.getEntityClass()));
				if (filterProcessor != null) {
					filterProcessor.process(criteria, filter, pagination);
				}
				return callback.doInQuery(criteria.assign());
			}
		});
	}

	public static interface QueryCallback<T> {

		T doInQuery(Query query);

	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("executor", executor);
		builder.append("filterProcessor", filterProcessor);
		return builder.toString();
	}
}
