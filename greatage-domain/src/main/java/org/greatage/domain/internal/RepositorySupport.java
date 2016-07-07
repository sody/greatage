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
import org.greatage.domain.Query;
import org.greatage.domain.Repository;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class RepositorySupport {
    private static final ThreadLocal<Repository> REPOSITORY = new InheritableThreadLocal<Repository>();

    public static <PK extends Serializable, E extends Entity<PK>>
    E read(final E entity) {
        return repository().read(entity);
    }

    public static <PK extends Serializable, E extends Entity<PK>>
    Map<PK, E> readAll(final E... entities) {
        return repository().readAll(entities);
    }

    public static <PK extends Serializable, E extends Entity<PK>>
    Map<PK, E> readAll(final Iterable<E> entities) {
        return repository().readAll(entities);
    }

    public static <PK extends Serializable, E extends Entity<PK>>
    E read(final Class<E> entityClass, final PK key) {
        return repository().read(entityClass, key);
    }

    public static <PK extends Serializable, E extends Entity<PK>>
    Map<PK, E> readAll(final Class<E> entityClass, final PK... keys) {
        return repository().readAll(entityClass, keys);
    }

    public static <PK extends Serializable, E extends Entity<PK>>
    Map<PK, E> readAll(final Class<E> entityClass, final Iterable<PK> keys) {
        return repository().readAll(entityClass, keys);
    }

    public static <PK extends Serializable, E extends Entity<PK>>
    void create(final E entity) {
        repository().create(entity);
    }

    public static <PK extends Serializable, E extends Entity<PK>>
    void createAll(final E... entities) {
        repository().createAll(entities);
    }

    public static <PK extends Serializable, E extends Entity<PK>>
    void createAll(final Iterable<E> entities) {
        repository().createAll(entities);
    }

    public static <PK extends Serializable, E extends Entity<PK>>
    void update(final E entity) {
        repository().update(entity);
    }

    public static <PK extends Serializable, E extends Entity<PK>>
    void updateAll(final E... entities) {
        repository().updateAll(entities);
    }

    public static <PK extends Serializable, E extends Entity<PK>>
    void updateAll(final Iterable<E> entities) {
        repository().updateAll(entities);
    }

    public static <PK extends Serializable, E extends Entity<PK>>
    void delete(final E entity) {
        repository().delete(entity);
    }

    public static <PK extends Serializable, E extends Entity<PK>>
    void deleteAll(final E... entities) {
        repository().deleteAll(entities);
    }

    public static <PK extends Serializable, E extends Entity<PK>>
    void deleteAll(final Iterable<E> entities) {
        repository().deleteAll(entities);
    }

    public static <PK extends Serializable, E extends Entity<PK>>
    void delete(final Class<E> entityClass, final PK key) {
        repository().delete(entityClass, key);
    }

    public static <PK extends Serializable, E extends Entity<PK>>
    void deleteAll(final Class<E> entityClass, final PK... keys) {
        repository().deleteAll(entityClass, keys);
    }

    public static <PK extends Serializable, E extends Entity<PK>>
    void deleteAll(final Class<E> entityClass, final Iterable<PK> keys) {
        repository().deleteAll(entityClass, keys);
    }

    public static <PK extends Serializable, E extends Entity<PK>>
    void save(final E entity) {
        repository().save(entity);
    }

    public static <PK extends Serializable, E extends Entity<PK>>
    void saveAll(final E... entities) {
        repository().saveAll(entities);
    }

    public static <PK extends Serializable, E extends Entity<PK>>
    void saveAll(final Iterable<E> entities) {
        repository().saveAll(entities);
    }

    public static <PK extends Serializable, E extends Entity<PK>>
    Query<PK, E> query(final Class<E> entityClass) {
        return repository().query(entityClass);
    }

    public static void execute(final Repository repository, final Runnable runnable) {
        final Repository original = REPOSITORY.get();
        try {
            REPOSITORY.set(repository);
            runnable.run();
        } finally {
            if (original != null) {
                REPOSITORY.set(original);
            } else {
                REPOSITORY.remove();
            }
        }
    }

    public static <V> V execute(final Repository repository, final Callable<V> callable) throws Exception {
        final Repository original = REPOSITORY.get();
        try {
            REPOSITORY.set(repository);
            return callable.call();
        } finally {
            if (original != null) {
                REPOSITORY.set(original);
            } else {
                REPOSITORY.remove();
            }
        }
    }

    protected static Repository repository() {
        final Repository repository = REPOSITORY.get();
        if (repository == null) {
            throw new IllegalStateException("No repository is associated with current thread.");
        }

        return repository;
    }
}
