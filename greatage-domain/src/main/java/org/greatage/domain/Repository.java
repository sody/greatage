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

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * This interface represents a main access point for Domain API which function is to offer
 * create, read, update and delete operations (CRUD) for persistable domain objects.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Repository {

    /**
     * Reads single entity data by its primary key.
     *
     * @param entityClass entity class, not {@code null}
     * @param key         entity primary key, not {@code null}
     * @param <PK>        type of entity primary key
     * @param <E>         type of entity
     * @return single entity data by primary key or {@code null} if not found
     * @throws NullPointerException if specified key is null
     */
    <PK extends Serializable, E extends Entity<PK>>
    E read(Class<E> entityClass, PK key);

    /**
     * Reads multiple entities data by their primary keys.
     *
     * @param entityClass entity class, not {@code null}
     * @param keys        entities primary keys, not {@code null}
     * @param <PK>        type of entity primary key
     * @param <E>         type of entity
     * @return detailed entity data mapped by primary keys or {@code null}s for entities that are not found
     * @throws NullPointerException if specified keys or one of the keys is {@code null}
     */
    <PK extends Serializable, E extends Entity<PK>>
    Map<PK, E> readAll(Class<E> entityClass, PK... keys);

    /**
     * Reads multiple entities data by their primary keys.
     *
     * @param entityClass entity class, not {@code null}
     * @param keys        entities primary keys, not {@code null}
     * @param <PK>        type of entity primary key
     * @param <E>         type of entity
     * @return detailed entity data mapped by primary keys or {@code null}s for entities that are not found
     * @throws NullPointerException if specified keys or one of the keys is {@code null}
     */
    <PK extends Serializable, E extends Entity<PK>>
    Map<PK, E> readAll(Class<E> entityClass, Collection<PK> keys);

    /**
     * Makes entity persistent by saving it into repository.
     *
     * @param entity entity
     * @param <PK>   type of entity primary key
     * @param <E>    type of entity
     */
    <PK extends Serializable, E extends Entity<PK>>
    void insert(E entity);

    /**
     * Updates entity state in repository.
     *
     * @param entity entity
     * @param <PK>   type of entity primary key
     * @param <E>    type of entity
     */
    <PK extends Serializable, E extends Entity<PK>>
    void update(E entity);

    /**
     * Makes entity persistent if it is not or updates entity state in repository otherwise.
     *
     * @param entity entity
     * @param <PK>   type of entity primary key
     * @param <E>    type of entity
     */
    <PK extends Serializable, E extends Entity<PK>>
    void save(E entity);

    /**
     * Deletes entity from repository.
     *
     * @param entity entity
     * @param <PK>   type of entity primary key
     * @param <E>    type of entity
     */
    <PK extends Serializable, E extends Entity<PK>>
    void remove(E entity);

    <PK extends Serializable, E extends Entity<PK>>
    Query<PK, E> query(Class<E> entityClass);
}
