/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository;

import org.greatage.domain.Entity;
import org.greatage.domain.Pagination;
import org.greatage.util.DescriptionBuilder;
import org.greatage.util.ReflectionUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractEntityRepository implements EntityRepository {
	private final Map<Class, Class> entityMapping;

	protected AbstractEntityRepository(final Map<Class, Class> entityMapping) {
		this.entityMapping = entityMapping;
	}

	public <PK extends Serializable, E extends Entity<PK>>
	int count(final Class<E> entityClass) {
		return count(createDefaultFilter(entityClass));
	}

	public <PK extends Serializable, E extends Entity<PK>>
	List<E> find(final Class<E> entityClass, final Pagination pagination) {
		return find(createDefaultFilter(entityClass), pagination);
	}

	public <PK extends Serializable, E extends Entity<PK>>
	List<PK> findKeys(final Class<E> entityClass, final Pagination pagination) {
		return findKeys(createDefaultFilter(entityClass), pagination);
	}

	public <PK extends Serializable, E extends Entity<PK>>
	List<Map<String, Object>> findValueObjects(final Class<E> entityClass, final Map<String, String> projection, final Pagination pagination) {
		return findValueObjects(createDefaultFilter(entityClass), projection, pagination);
	}

	public <PK extends Serializable, E extends Entity<PK>>
	E create(final Class<E> entityClass) {
		return ReflectionUtils.newInstance(getImplementation(entityClass));
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void saveOrUpdate(final E entity) {
		if (entity.isNew()) {
			save(entity);
		} else {
			update(entity);
		}
	}

	/**
	 * Creates default entity filter for entityClass.
	 *
	 * @param entityClass entity class
	 * @param <PK>        type of entities primary key
	 * @param <E>         type of entities
	 * @return default entity filter for entityClass
	 */
	protected <PK extends Serializable, E extends Entity<PK>>
	EntityFilter<PK, E> createDefaultFilter(final Class<E> entityClass) {
		return new DefaultEntityFilter<PK, E>(entityClass);
	}

	@SuppressWarnings({"unchecked"})
	protected <T> Class<? extends T> getImplementation(final Class<T> entityClass) {
		final Class implementation = entityMapping.get(entityClass);
		return implementation != null ? implementation : entityClass;
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("mapping", entityMapping);
		return builder.toString();
	}
}
