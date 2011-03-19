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

import org.greatage.domain.*;
import org.greatage.util.DescriptionBuilder;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * This class represents hibernate based implementation of {@link org.greatage.domain.EntityRepository}. Selection
 * methods based on filterProcessor parameter.
 *
 * @author Ivan Khalopik
 * @see org.greatage.domain.EntityFilterProcessor
 * @since 1.0
 */
public class HibernateRepository extends AbstractEntityRepository {
	private final HibernateExecutor executor;
	private final EntityFilterProcessor filterProcessor;

	public HibernateRepository(final Map<Class, Class> entityMapping,
							   final HibernateExecutor executor,
							   final EntityFilterProcessor filterProcessor) {
		super(entityMapping);
		this.executor = executor;
		this.filterProcessor = filterProcessor;
	}

	public <PK extends Serializable, E extends Entity<PK>>
	int count(final EntityFilter<PK, E> filter) {
		return execute(filter, Pagination.ALL, new CriteriaCallback<Number>() {
			public Number doInCriteria(final Criteria criteria) {
				return (Number) criteria.setProjection(Projections.rowCount()).uniqueResult();
			}
		}).intValue();
	}

	public <PK extends Serializable, E extends Entity<PK>>
	List<E> find(final EntityFilter<PK, E> filter, final Pagination pagination) {
		return execute(filter, pagination, new CriteriaCallback<List<E>>() {
			@SuppressWarnings({"unchecked"})
			public List<E> doInCriteria(final Criteria criteria) {
				return criteria.list();
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	List<PK> findKeys(final EntityFilter<PK, E> filter, final Pagination pagination) {
		return execute(filter, pagination, new CriteriaCallback<List<PK>>() {
			@SuppressWarnings({"unchecked"})
			public List<PK> doInCriteria(final Criteria criteria) {
				return criteria.setProjection(Projections.distinct(Projections.id())).list();
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	List<Map<String, Object>> findValueObjects(final EntityFilter<PK, E> filter, final Map<String, String> projection, final Pagination pagination) {
		throw new UnsupportedOperationException();
	}

	public <PK extends Serializable, E extends Entity<PK>>
	E findUnique(final EntityFilter<PK, E> filter) {
		return execute(filter, Pagination.UNIQUE, new CriteriaCallback<E>() {
			@SuppressWarnings({"unchecked"})
			public E doInCriteria(final Criteria criteria) {
				return (E) criteria.uniqueResult();
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	E get(final Class<E> entityClass, final PK pk) {
		return executor.execute(new HibernateCallback<E>() {
			@SuppressWarnings({"unchecked"})
			public E doInSession(final Session session) throws Throwable {
				return (E) session.get(getImplementation(entityClass), pk);
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void save(final E entity) {
		executor.execute(new HibernateCallback<Object>() {
			public Object doInSession(final Session session) throws Throwable {
				session.save(entity);
				return null;
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void update(final E entity) {
		executor.execute(new HibernateCallback<Object>() {
			public Object doInSession(final Session session) throws Throwable {
				session.update(entity);
				return null;
			}
		});
	}

	@Override
	public <PK extends Serializable, E extends Entity<PK>>
	void saveOrUpdate(final E entity) {
		executor.execute(new HibernateCallback<Object>() {
			public Object doInSession(final Session session) throws Throwable {
				session.saveOrUpdate(entity);
				return null;
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void delete(final E entity) {
		executor.execute(new HibernateCallback<Object>() {
			public Object doInSession(final Session session) throws Throwable {
				session.delete(entity);
				return null;
			}
		});
	}

	/**
	 * Executes inner hibernate selection by filter with specified callback. First creates {@link DetachedCriteria} object
	 * according to filter and pagination parameters and then executes it with criteria callback.
	 *
	 * @param filter	 selection filter
	 * @param pagination selection pagination
	 * @param callback   criteria callback
	 * @param <T>        type of returned value
	 * @param <PK>       type of entities primary key
	 * @param <E>        type of entities
	 * @return criteria execution result
	 */
	private <T, PK extends Serializable, E extends Entity<PK>>
	T execute(final EntityFilter<PK, E> filter, final Pagination pagination, final CriteriaCallback<T> callback) {
		final HibernateCriteria entityCriteria = HibernateCriteria.forClass(getImplementation(filter.getEntityClass()));
		if (filterProcessor != null) {
			filterProcessor.process(entityCriteria, filter, pagination);
		}
		return executor.execute(new HibernateCallback<T>() {
			public T doInSession(final Session session) throws Throwable {
				return callback.doInCriteria(entityCriteria.assign(session));
			}
		});
	}

	public static interface CriteriaCallback<T> {

		/**
		 * Executes some logic for returning values from criteria.
		 *
		 * @param criteria hibernate criteria holder
		 * @return criteria execution result
		 */
		T doInCriteria(Criteria criteria);

	}

	@Override
	public String toString() {
		final DescriptionBuilder sb = new DescriptionBuilder(getClass());
		sb.append("executor", executor);
		sb.append("filterProcessor", filterProcessor);
		return sb.toString();
	}
}
