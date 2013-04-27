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

package org.greatage.domain.internal;

import org.greatage.domain.Entity;
import org.greatage.domain.Repository;
import org.greatage.util.DescriptionBuilder;
import org.greatage.util.ReflectionUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractRepository implements Repository {
    private final Map<Class, Class> entityMapping;

    protected AbstractRepository(final Map<Class, Class> entityMapping) {
        this.entityMapping = entityMapping;
    }

    public <PK extends Serializable, E extends Entity<PK>>
    E create(final Class<E> entityClass) {
        return ReflectionUtils.newInstance(getImplementation(entityClass));
    }

    public <PK extends Serializable, E extends Entity<PK>>
    void save(final E entity) {
        if (entity.isNew()) {
            insert(entity);
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
