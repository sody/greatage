package org.greatage.domain.objectify;

import com.google.appengine.api.datastore.Transaction;
import com.googlecode.objectify.Objectify;
import org.greatage.domain.Entity;
import org.greatage.domain.TransactionExecutor;
import org.greatage.domain.internal.AbstractRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ObjectifyRepository extends AbstractRepository {
	private final TransactionExecutor<Transaction, Objectify> executor;

	public ObjectifyRepository(final TransactionExecutor<Transaction, Objectify> executor,
							   final Map<Class, Class> entityMapping) {
		super(entityMapping);
		this.executor = executor;
	}

	public <PK extends Serializable, E extends Entity<PK>>
	E get(final Class<E> entityClass, final PK pk) {
		return executor.execute(new TransactionExecutor.SessionCallback<E, Objectify>() {
			public E doInSession(final Objectify session) throws Exception {
				return session.get(getImplementation(entityClass), (Long) pk);
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void save(final E entity) {
		executor.execute(new TransactionExecutor.SessionCallback<Object, Objectify>() {
			public Object doInSession(final Objectify session) throws Exception {
				session.put(entity);
				return null;
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void update(final E entity) {
		executor.execute(new TransactionExecutor.SessionCallback<Object, Objectify>() {
			public Object doInSession(final Objectify session) throws Exception {
				session.put(entity);
				return null;
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void delete(final E entity) {
		executor.execute(new TransactionExecutor.SessionCallback<Object, Objectify>() {
			public Object doInSession(final Objectify session) throws Exception {
				session.delete(entity);
				return null;
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>> Query<PK, E> query(final Class<E> entityClass) {
		return new ObjectifyQuery<PK, E>(entityClass);
	}

	private <T, PK extends Serializable, E extends Entity<PK>>
	T execute(final ObjectifyQuery<PK, E> query, final QueryCallback<T, E> callback) {
		return executor.execute(new TransactionExecutor.SessionCallback<T, Objectify>() {
			public T doInSession(final Objectify session) throws Exception {
				final com.googlecode.objectify.Query<? extends E> signedQuery = session.query(getImplementation(query.entityClass));

				final ObjectifyQueryVisitor<PK, E> visitor = new ObjectifyQueryVisitor<PK, E>(signedQuery);
				visitor.visitCriteria(query.criteria);

				if (query.start > 0) {
					signedQuery.offset(query.start);
				}
				if (query.count >= 0) {
					signedQuery.limit(query.count);
				}

				return callback.doInQuery(signedQuery);
			}
		});
	}


	private static interface QueryCallback<T, E> {

		T doInQuery(com.googlecode.objectify.Query<? extends E> query);
	}

	class ObjectifyQuery<PK extends Serializable, E extends Entity<PK>> implements Query<PK, E> {
		private final Class<E> entityClass;

		private Criteria<PK, E> criteria;
		private int start = 0;
		private int count = -1;

		ObjectifyQuery(final Class<E> entityClass) {
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
			return execute(this, new QueryCallback<Number, E>() {
				public Number doInQuery(final com.googlecode.objectify.Query<? extends E> query) {
					return query.count();
				}
			}).longValue();
		}

		public List<E> list() {
			return execute(this, new QueryCallback<List<E>, E>() {
				public List<E> doInQuery(final com.googlecode.objectify.Query<? extends E> query) {
					//noinspection unchecked
					return (List) query.list();
				}
			});
		}

		public E unique() {
			return execute(this, new QueryCallback<E, E>() {
				public E doInQuery(final com.googlecode.objectify.Query<? extends E> query) {
					return query.get();
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


}
