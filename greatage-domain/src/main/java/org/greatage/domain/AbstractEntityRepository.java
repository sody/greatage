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
	long count(final Class<E> entityClass) {
		return count(entityClass, new AllCriteria<PK, E>());
	}

	public <PK extends Serializable, E extends Entity<PK>>
	List<E> find(final Class<E> entityClass, final Pagination pagination) {
		return find(entityClass, new AllCriteria<PK, E>(), pagination);
	}

	public <PK extends Serializable, E extends Entity<PK>>
	List<PK> findKeys(final Class<E> entityClass, final Pagination pagination) {
		return findKeys(entityClass, new AllCriteria<PK, E>(), pagination);
	}

	public <PK extends Serializable, E extends Entity<PK>>
	List<Map<String, Object>> findValueObjects(final Class<E> entityClass, final Map<String, String> projection, final Pagination pagination) {
		return findValueObjects(entityClass, new AllCriteria<PK, E>(), projection, pagination);
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
