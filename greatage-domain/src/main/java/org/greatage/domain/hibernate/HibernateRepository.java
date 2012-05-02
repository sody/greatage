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

import org.greatage.domain.Entity;
import org.greatage.domain.TransactionExecutor;
import org.greatage.domain.internal.AbstractRepository;
import org.greatage.util.DescriptionBuilder;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * This class represents hibernate based implementation of {@link org.greatage.domain.Repository}. Selection
 * methods based on filterProcessor parameter.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HibernateRepository extends AbstractRepository {
	private final TransactionExecutor<Transaction, Session> executor;

	public HibernateRepository(final TransactionExecutor<Transaction, Session> executor, final Map<Class, Class> entityMapping) {
		super(entityMapping);
		this.executor = executor;
	}

	public <PK extends Serializable, E extends Entity<PK>>
	E get(final Class<E> entityClass, final PK pk) {
		return executor.execute(new TransactionExecutor.SessionCallback<E, Session>() {
			@SuppressWarnings({"unchecked"})
			public E doInSession(final Session session) throws Exception {
				return (E) session.get(getImplementation(entityClass), pk);
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void save(final E entity) {
		executor.execute(new TransactionExecutor.SessionCallback<Object, Session>() {
			public Object doInSession(final Session session) throws Exception {
				session.save(entity);
				return null;
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void update(final E entity) {
		executor.execute(new TransactionExecutor.SessionCallback<Object, Session>() {
			public Object doInSession(final Session session) throws Exception {
				session.update(entity);
				return null;
			}
		});
	}

	@Override
	public <PK extends Serializable, E extends Entity<PK>>
	void saveOrUpdate(final E entity) {
		executor.execute(new TransactionExecutor.SessionCallback<Object, Session>() {
			public Object doInSession(final Session session) throws Exception {
				session.saveOrUpdate(entity);
				return null;
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void delete(final E entity) {
		executor.execute(new TransactionExecutor.SessionCallback<Object, Session>() {
			public Object doInSession(final Session session) throws Exception {
				session.delete(entity);
				return null;
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>> Query<PK, E> query(final Class<E> entityClass) {
		return new HibernateQuery<PK, E>(entityClass);
	}

	/**
	 * Executes inner hibernate selection by filter with specified callback. First creates {@link org.hibernate.criterion.DetachedCriteria} object
	 * according to filter and pagination parameters and then executes it with criteria callback.
	 *
	 * @param callback criteria callback
	 * @param <T>      type of returned value
	 * @param <PK>     type of entities primary key
	 * @param <E>      type of entities
	 * @return criteria execution result
	 */
	private <T, PK extends Serializable, E extends Entity<PK>>
	T execute(final HibernateQuery<PK, E> query, final CriteriaCallback<T> callback) {
		return executor.execute(new TransactionExecutor.SessionCallback<T, Session>() {
			public T doInSession(final Session session) throws Exception {
				final org.hibernate.Criteria signedCriteria = session.createCriteria(getImplementation(query.entityClass));
				new HibernateQueryVisitor<PK, E>(signedCriteria).visitCriteria(query.criteria);

				if (query.start > 0) {
					signedCriteria.setFirstResult(query.start);
				}
				if (query.count >= 0) {
					signedCriteria.setMaxResults(query.count);
				}

				return callback.doInCriteria(signedCriteria);
			}
		});
	}

	private static interface CriteriaCallback<T> {

		/**
		 * Executes some logic for returning values from criteria.
		 *
		 * @param criteria hibernate criteria holder
		 * @return criteria execution result
		 */
		T doInCriteria(org.hibernate.Criteria criteria);
	}

	private class HibernateQuery<PK extends Serializable, E extends Entity<PK>> implements Query<PK, E> {
		private final Class<E> entityClass;

		private Criteria<PK, E> criteria;
		private int start = 0;
		private int count = -1;

		HibernateQuery(final Class<E> entityClass) {
			this.entityClass = entityClass;
		}

		public Query<PK, E> filter(final Criteria<PK, E> criteria) {
			this.criteria = this.criteria != null ?
					this.criteria.and(criteria) :
					criteria;

			return this;
		}

		public Query<PK, E> fetch(final Property property) {
			throw new UnsupportedOperationException();
		}

		public Query<PK, E> sort(final Property property, final boolean ascending, final boolean ignoreCase) {
			throw new UnsupportedOperationException();
		}

		public Query<PK, E> map(final Property property, final String key) {
			throw new UnsupportedOperationException();
		}

		public Query<PK, E> paginate(final int start, final int count) {
			this.start = start;
			this.count = count;

			return this;
		}

		public long count() {
			return execute(this, new CriteriaCallback<Number>() {
				public Number doInCriteria(final org.hibernate.Criteria criteria) {
					return (Number) criteria.setProjection(Projections.rowCount()).uniqueResult();
				}
			}).longValue();
		}

		public List<E> list() {
			return execute(this, new CriteriaCallback<List<E>>() {
				@SuppressWarnings({"unchecked"})
				public List<E> doInCriteria(final org.hibernate.Criteria criteria) {
					return criteria.list();
				}
			});
		}

		public E unique() {
			return execute(this, new CriteriaCallback<E>() {
				@SuppressWarnings({"unchecked"})
				public E doInCriteria(final org.hibernate.Criteria criteria) {
					return (E) criteria.uniqueResult();
				}
			});
		}

		public List<PK> keys() {
			return execute(this, new CriteriaCallback<List<PK>>() {
				@SuppressWarnings({"unchecked"})
				public List<PK> doInCriteria(final org.hibernate.Criteria criteria) {
					return criteria.setProjection(Projections.distinct(Projections.id())).list();
				}
			});
		}

		public List<Map<String, Object>> projections() {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public String toString() {
		final DescriptionBuilder sb = new DescriptionBuilder(getClass());
		sb.append("executor", executor);
		return sb.toString();
	}
}
