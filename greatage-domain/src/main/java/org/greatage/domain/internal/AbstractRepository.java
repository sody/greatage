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

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
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

    @SuppressWarnings("unchecked")
    @Override
    public <PK extends Serializable, E extends Entity<PK>>
    E read(E entity) {
        return (E) read(entity.getClass(), entity.getId());
    }

    @Override
    public <PK extends Serializable, E extends Entity<PK>>
    Map<PK, E> readAll(final E... entities) {
        return readAll(Arrays.asList(entities));
    }

    @Override
    public <PK extends Serializable, E extends Entity<PK>>
    Map<PK, E> readAll(final Iterable<E> entities) {
        final Map<PK, E> entitiesByKey = new HashMap<PK, E>();
        for (E entity : entities) {
            entitiesByKey.put(entity.getId(), read(entity));
        }
        return entitiesByKey;
    }

    @Override
    public <PK extends Serializable, E extends Entity<PK>>
    Map<PK, E> readAll(final Class<E> entityClass, final PK... keys) {
        return readAll(entityClass, Arrays.asList(keys));
    }

    @Override
    public <PK extends Serializable, E extends Entity<PK>>
    Map<PK, E> readAll(final Class<E> entityClass, final Iterable<PK> keys) {
        final Map<PK, E> entitiesByKey = new HashMap<PK, E>();
        for (PK key : keys) {
            entitiesByKey.put(key, read(entityClass, key));
        }
        return entitiesByKey;
    }

    @Override
    public <PK extends Serializable, E extends Entity<PK>>
    void createAll(final E... entities) {
        createAll(Arrays.asList(entities));
    }

    @Override
    public <PK extends Serializable, E extends Entity<PK>>
    void createAll(final Iterable<E> entities) {
        for (E entity : entities) {
            create(entity);
        }
    }

    @Override
    public <PK extends Serializable, E extends Entity<PK>>
    void updateAll(final E... entities) {
        updateAll(Arrays.asList(entities));
    }

    @Override
    public <PK extends Serializable, E extends Entity<PK>>
    void updateAll(final Iterable<E> entities) {
        for (E entity : entities) {
            update(entity);
        }
    }

    @Override
    public <PK extends Serializable, E extends Entity<PK>>
    void deleteAll(final E... entities) {
        deleteAll(Arrays.asList(entities));
    }

    @Override
    public <PK extends Serializable, E extends Entity<PK>>
    void deleteAll(final Iterable<E> entities) {
        for (E entity : entities) {
            delete(entity);
        }
    }

    @Override
    public <PK extends Serializable, E extends Entity<PK>>
    void delete(final Class<E> entityClass, final PK key) {
        // dirty hack
        delete(read(entityClass, key));
    }

    @Override
    public <PK extends Serializable, E extends Entity<PK>>
    void deleteAll(final Class<E> entityClass, final PK... keys) {
        deleteAll(entityClass, Arrays.asList(keys));
    }

    @Override
    public <PK extends Serializable, E extends Entity<PK>>
    void deleteAll(final Class<E> entityClass, final Iterable<PK> keys) {
        for (PK key : keys) {
            delete(entityClass, key);
        }
    }

    @Override
    public <PK extends Serializable, E extends Entity<PK>>
    void save(final E entity) {
        if (entity.isNew()) {
            create(entity);
        } else {
            update(entity);
        }
    }

    @Override
    public <PK extends Serializable, E extends Entity<PK>>
    void saveAll(final E... entities) {
        saveAll(Arrays.asList(entities));
    }

    @Override
    public <PK extends Serializable, E extends Entity<PK>>
    void saveAll(final Iterable<E> entities) {
        for (E entity : entities) {
            save(entity);
        }
    }

    @SuppressWarnings({"unchecked"})
    protected <T> Class<? extends T> getImplementation(final Class<T> entityClass) {
        final Class implementation = entityMapping.get(entityClass);
        return implementation != null ? implementation : entityClass;
    }
}
