/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository.jpa;

import org.greatage.domain.Entity;
import org.greatage.domain.Pagination;
import org.greatage.domain.repository.AbstractEntityRepository;
import org.greatage.domain.repository.EntityFilter;
import org.greatage.domain.repository.EntityFilterProcessor;
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
	private final EntityFilterProcessor filterProcessor;


	public JpaRepository(final JpaExecutor executor, final EntityFilterProcessor filterProcessor) {
		this.executor = executor;
		this.filterProcessor = filterProcessor;
	}

	public <PK extends Serializable, E extends Entity<PK>>
	int count(final EntityFilter<PK, E> filter) {
		return execute("select count() from entityClass", filter, Pagination.ALL, new QueryCallback<Integer>() {
			public Integer doInQuery(final Query query) {
				return (Integer) query.getSingleResult();
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	List<E> find(final EntityFilter<PK, E> filter, final Pagination pagination) {
		return execute("select from entityClass", filter, pagination, new QueryCallback<List<E>>() {
			@SuppressWarnings({"unchecked"})
			public List<E> doInQuery(final Query query) {
				return query.getResultList();
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	List<PK> findKeys(final EntityFilter<PK, E> filter, final Pagination pagination) {
		throw new UnsupportedOperationException("cannot find keys");
	}

	public <PK extends Serializable, E extends Entity<PK>>
	List<Map<String, Object>> findValueObjects(final EntityFilter<PK, E> filter, final Map<String, String> projection, final Pagination pagination) {
		throw new UnsupportedOperationException("cannot find value objects");
	}

	public <PK extends Serializable, E extends Entity<PK>>
	E findUnique(final EntityFilter<PK, E> filter) {
		return execute("select from entityClass", filter, Pagination.UNIQUE, new QueryCallback<E>() {
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
	T execute(final String queryString, final EntityFilter<PK, E> filter, final Pagination pagination, final QueryCallback<T> callback) {
		return executor.execute(new JpaCallback<T>() {
			public T doInJpa(EntityManager em) throws PersistenceException {
				final Query query = em.createQuery(queryString.replaceAll("entityClass", filter.getEntityClass().getName()));
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
		builder.append("filterProcessor", filterProcessor);
		return builder.toString();
	}
}
