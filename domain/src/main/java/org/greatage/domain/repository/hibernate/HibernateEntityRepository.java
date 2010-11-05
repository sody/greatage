/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository.hibernate;

import org.greatage.domain.Entity;
import org.greatage.domain.Pagination;
import org.greatage.domain.repository.AbstractEntityRepository;
import org.greatage.domain.repository.EntityFilter;
import org.greatage.domain.repository.EntityFilterProcessor;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * This class represents hibernate based implementation of {@link org.greatage.domain.repository.EntityRepository}. Selection
 * methods based on filterProcessor parameter.
 *
 * @author Ivan Khalopik
 * @see org.greatage.domain.repository.EntityFilterProcessor
 * @since 1.0
 */
public class HibernateEntityRepository extends AbstractEntityRepository {
	private HibernateTemplate hibernateTemplate;
	private EntityFilterProcessor filterProcessor;

	/**
	 * Sets filter processor for work with entity filters.
	 *
	 * @param filterProcessor filter processor
	 */
	public void setFilterProcessor(EntityFilterProcessor filterProcessor) {
		this.filterProcessor = filterProcessor;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public <PK extends Serializable, E extends Entity<PK>>
	int count(EntityFilter<PK, E> filter) {
		return execute(filter, Pagination.ALL, new CriteriaCallback<Number>() {
			public Number doInCriteria(Criteria criteria) {
				return (Number) criteria.setProjection(Projections.rowCount()).uniqueResult();
			}
		}).intValue();
	}

	public <PK extends Serializable, E extends Entity<PK>>
	List<E> find(EntityFilter<PK, E> filter, Pagination pagination) {
		return execute(filter, pagination, new CriteriaCallback<List<E>>() {
			@SuppressWarnings({"unchecked"})
			public List<E> doInCriteria(Criteria criteria) {
				return criteria.list();
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	List<PK> findKeys(EntityFilter<PK, E> filter, Pagination pagination) {
		return execute(filter, pagination, new CriteriaCallback<List<PK>>() {
			@SuppressWarnings({"unchecked"})
			public List<PK> doInCriteria(final Criteria criteria) {
				return criteria.setProjection(Projections.distinct(Projections.id())).list();
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	List<Map<String, Object>> findValueObjects(EntityFilter<PK, E> filter, final Map<String, String> projection, Pagination pagination) {
		throw new UnsupportedOperationException();
	}

	public <PK extends Serializable, E extends Entity<PK>>
	E findUnique(EntityFilter<PK, E> filter) {
		return execute(filter, Pagination.UNIQUE, new CriteriaCallback<E>() {
			@SuppressWarnings({"unchecked"})
			public E doInCriteria(final Criteria criteria) {
				return (E) criteria.uniqueResult();
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	E get(Class<E> entityClass, PK pk) {
		return hibernateTemplate.get(getImplementation(entityClass), pk);
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void save(E entity) {
		hibernateTemplate.save(entity);
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void update(E entity) {
		hibernateTemplate.update(entity);
	}

	@Override
	public <PK extends Serializable, E extends Entity<PK>>
	void saveOrUpdate(E entity) {
		hibernateTemplate.saveOrUpdate(entity);
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void delete(E entity) {
		hibernateTemplate.delete(entity);
	}

	/**
	 * Executes inner hibernate selection by filter with specified callback. First creates {@link DetachedCriteria} object
	 * according to filter and pagination parameters and then executes it with criteria callback.
	 *
	 * @param filter	 selection filter
	 * @param pagination selection pagination
	 * @param callback   criteria callback
	 * @param <R>        type of returned value
	 * @param <PK>       type of entities primary key
	 * @param <E>        type of entities
	 * @return criteria execution result
	 */
	protected <R, PK extends Serializable, E extends Entity<PK>>
	R execute(final EntityFilter<PK, E> filter, final Pagination pagination, final CriteriaCallback<R> callback) {
		final HibernateEntityCriteria entityCriteria = HibernateEntityCriteria.forClass(getImplementation(filter.getEntityClass()));
		if (filterProcessor != null) {
			filterProcessor.process(entityCriteria, filter, pagination);
		}
		return hibernateTemplate.execute(new HibernateCallback<R>() {
			public R doInHibernate(final Session session) throws HibernateException, SQLException {
				return callback.doInCriteria(entityCriteria.assign(session));
			}
		});
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("HibernateEntityRepository(");
		sb.append("hibernateTemplate=").append(hibernateTemplate);
		sb.append("filterProcessor=").append(filterProcessor);
		sb.append(")");
		return sb.toString();
	}
}
