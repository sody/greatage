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

import org.greatage.domain.Entity;
import org.greatage.domain.TransactionExecutor;
import org.greatage.domain.internal.AbstractRepository;
import org.greatage.util.DescriptionBuilder;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JPARepository extends AbstractRepository {
	private final TransactionExecutor<EntityTransaction, EntityManager> executor;

	public JPARepository(final TransactionExecutor<EntityTransaction, EntityManager> executor,
						 final Map<Class, Class> entityMapping) {
		super(entityMapping);
		this.executor = executor;
	}

	public <PK extends Serializable, E extends Entity<PK>>
	E get(final Class<E> entityClass, final PK pk) {
		return executor.execute(new TransactionExecutor.SessionCallback<E, EntityManager>() {
			public E doInSession(final EntityManager session) throws Exception {
				return session.find(getImplementation(entityClass), pk);
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void save(final E entity) {
		executor.execute(new TransactionExecutor.SessionCallback<Object, EntityManager>() {
			public Object doInSession(final EntityManager session) throws Exception {
				session.persist(entity);
				return null;
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void update(final E entity) {
		executor.execute(new TransactionExecutor.SessionCallback<Object, EntityManager>() {
			public Object doInSession(final EntityManager session) throws Exception {
				session.merge(entity);
				return null;
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void delete(final E entity) {
		executor.execute(new TransactionExecutor.SessionCallback<Object, EntityManager>() {
			public Object doInSession(final EntityManager session) throws Exception {
				session.remove(entity);
				return null;
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>> Query<PK, E> query(final Class<E> entityClass) {
		return new JPAQuery<PK, E>(entityClass);
	}

	protected <T, PK extends Serializable, E extends Entity<PK>>
	T execute(final String queryString, final JPAQuery<PK, E> query, final QueryCallback<T> callback) {
		return executor.execute(new TransactionExecutor.SessionCallback<T, EntityManager>() {
			public T doInSession(final EntityManager session) throws Exception {
				final javax.persistence.Query signedQuery =
						session.createQuery(queryString.replaceAll("entityClass", query.entityClass.getName()));

				if (query.start > 0) {
					signedQuery.setFirstResult(query.start);
				}
				if (query.count >= 0) {
					signedQuery.setMaxResults(query.count);
				}

				return callback.doInQuery(signedQuery);
			}
		});
	}

	public interface QueryCallback<T> {

		T doInQuery(javax.persistence.Query query);
	}

	class JPAQuery<PK extends Serializable, E extends Entity<PK>> implements Query<PK, E> {
		private final Class<E> entityClass;

		private Criteria<PK, E> criteria;
		private int start = 0;
		private int count = -1;

		JPAQuery(final Class<E> entityClass) {
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
			return execute("select count() from entityClass", this, new QueryCallback<Number>() {
				public Number doInQuery(final javax.persistence.Query query) {
					return (Number) query.getSingleResult();
				}
			}).longValue();
		}

		public List<E> list() {
			return execute("select from entityClass", this, new QueryCallback<List<E>>() {
				@SuppressWarnings({"unchecked"})
				public List<E> doInQuery(final javax.persistence.Query query) {
					return query.getResultList();
				}
			});
		}

		public E unique() {
			return execute("select from entityClass", this, new QueryCallback<E>() {
				@SuppressWarnings({"unchecked"})
				public E doInQuery(final javax.persistence.Query query) {
					return (E) query.getSingleResult();
				}
			});
		}

		public List<PK> keys() {
			throw new UnsupportedOperationException();
		}

		public List<Map<String, Object>> projections() {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("executor", executor);
		return builder.toString();
	}
}
