/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository.jpa;

import org.greatage.domain.Entity;
import org.greatage.domain.Pagination;
import org.greatage.domain.repository.AbstractEntityRepository;
import org.greatage.domain.repository.EntityFilter;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.JpaTemplate;

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
public class JpaEntityRepository extends AbstractEntityRepository {
	private JpaTemplate jpaTemplate;

	public void setJpaTemplate(JpaTemplate jpaTemplate) {
		this.jpaTemplate = jpaTemplate;
	}

	public <PK extends Serializable, E extends Entity<PK>>
	int count(EntityFilter<PK, E> filter) {
		return execute("select count() from entityClass", filter, Pagination.ALL, new JpaQueryCallback<Integer>() {
			public Integer doInQuery(Query query) {
				return (Integer) query.getSingleResult();
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	List<E> find(EntityFilter<PK, E> filter, Pagination pagination) {
		return execute("select from entityClass", filter, pagination, new JpaQueryCallback<List<E>>() {
			@SuppressWarnings({"unchecked"})
			public List<E> doInQuery(Query query) {
				return query.getResultList();
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	List<PK> findKeys(EntityFilter<PK, E> filter, Pagination pagination) {
		throw new UnsupportedOperationException("cannot find keys");
	}

	public <PK extends Serializable, E extends Entity<PK>>
	List<Map<String, Object>> findValueObjects(EntityFilter<PK, E> filter, Map<String, String> projection, Pagination pagination) {
		throw new UnsupportedOperationException("cannot find value objects");
	}

	public <PK extends Serializable, E extends Entity<PK>>
	E findUnique(EntityFilter<PK, E> filter) {
		return execute("select from entityClass", filter, Pagination.UNIQUE, new JpaQueryCallback<E>() {
			@SuppressWarnings({"unchecked"})
			public E doInQuery(Query query) {
				return (E) query.getSingleResult();
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	E get(Class<E> entityClass, PK pk) {
		return jpaTemplate.find(entityClass, pk);
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void save(E entity) {
		jpaTemplate.persist(entity);
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void update(E entity) {
		jpaTemplate.merge(entity);
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void delete(E entity) {
		jpaTemplate.remove(entity);
	}

	protected <R, PK extends Serializable, E extends Entity<PK>>
	R execute(final String queryString, final EntityFilter<PK, E> filter, final Pagination pagination, final JpaQueryCallback<R> callback) {
		return jpaTemplate.execute(new JpaCallback<R>() {
			public R doInJpa(EntityManager em) throws PersistenceException {
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
}
