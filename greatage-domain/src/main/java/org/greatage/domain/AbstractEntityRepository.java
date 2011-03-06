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

package org.greatage.domain;

import org.greatage.util.DescriptionBuilder;
import org.greatage.util.ReflectionUtils;

import java.io.Serializable;
import java.util.Collection;
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

	class DefaultEntityFilter<PK extends Serializable, E extends Entity<PK>> implements EntityFilter<PK, E> {
		private final Class<E> entityClass;

		public DefaultEntityFilter(final Class<E> entityClass) {
			this.entityClass = entityClass;
		}

		public Class<E> getEntityClass() {
			return entityClass;
		}

		public Collection<PK> getIncludeKeys() {
			return null;
		}

		public Collection<PK> getExcludeKeys() {
			return null;
		}

		public String getQueryString() {
			return null;
		}
	}

}
