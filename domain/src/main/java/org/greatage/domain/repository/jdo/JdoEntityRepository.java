/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository.jdo;

import org.greatage.domain.Entity;
import org.greatage.domain.Pagination;
import org.greatage.domain.repository.AbstractEntityRepository;
import org.greatage.domain.repository.EntityFilter;
import org.greatage.domain.repository.EntityFilterProcessor;
import org.springframework.orm.jdo.JdoCallback;
import org.springframework.orm.jdo.JdoTemplate;

import javax.jdo.JDOException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 */
public class JdoEntityRepository extends AbstractEntityRepository {
	private static final String COUNT_RESULT = "count()";

	private EntityFilterProcessor filterProcessor;
	private JdoTemplate jdoTemplate;

	public void setFilterProcessor(EntityFilterProcessor filterProcessor) {
		this.filterProcessor = filterProcessor;
	}

	public void setJdoTemplate(final JdoTemplate jdoTemplate) {
		this.jdoTemplate = jdoTemplate;
	}

	public <PK extends Serializable, E extends Entity<PK>>
	int count(EntityFilter<PK, E> filter) {
		return execute(filter, Pagination.ALL, new JdoQueryCallback<Integer>() {
			public Integer doInQuery(Query query) {
				query.setResult(COUNT_RESULT);
				return (Integer) query.execute();
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	List<E> find(EntityFilter<PK, E> filter, Pagination pagination) {
		return execute(filter, pagination, new JdoQueryCallback<List<E>>() {
			@SuppressWarnings({"unchecked"})
			public List<E> doInQuery(Query query) {
				return (List<E>) query.execute();
			}
		});
	}

	//todo: implement this

	public <PK extends Serializable, E extends Entity<PK>>
	List<PK> findKeys(EntityFilter<PK, E> filter, Pagination pagination) {
		throw new UnsupportedOperationException("cannot find keys");
	}

	//todo: implement this

	public <PK extends Serializable, E extends Entity<PK>>
	List<Map<String, Object>> findValueObjects(EntityFilter<PK, E> filter, Map<String, String> projection, Pagination pagination) {
		throw new UnsupportedOperationException("cannot find value objects");
	}

	//todo: not tested

	public <PK extends Serializable, E extends Entity<PK>>
	E findUnique(EntityFilter<PK, E> filter) {
		return execute(filter, Pagination.UNIQUE, new JdoQueryCallback<E>() {
			@SuppressWarnings({"unchecked"})
			public E doInQuery(Query query) {
				query.setUnique(true);
				return (E) query.execute();
			}
		});
	}

	public <PK extends Serializable, E extends Entity<PK>>
	E get(Class<E> entityClass, PK pk) {
		return jdoTemplate.getObjectById(getImplementation(entityClass), pk);
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void save(E entity) {
		jdoTemplate.makePersistent(entity);
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void update(E entity) {
		jdoTemplate.refresh(entity);
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void delete(E entity) {
		jdoTemplate.deletePersistent(entity);
	}

	protected <R, PK extends Serializable, E extends Entity<PK>>
	R execute(final EntityFilter<PK, E> filter, final Pagination pagination, final JdoQueryCallback<R> callback) {
		return jdoTemplate.execute(new JdoCallback<R>() {
			public R doInJdo(PersistenceManager pm) throws JDOException {
				final JdoEntityCriteria criteria = JdoEntityCriteria.forClass(pm, getImplementation(filter.getEntityClass()));
				if (filterProcessor != null) {
					filterProcessor.process(criteria, filter, pagination);
				}
				return callback.doInQuery(criteria.assign());
			}
		});
	}
}
