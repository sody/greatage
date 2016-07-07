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
     * Reads entity state from repository. It will read entity state by primary key.
     *
     * @param entity entity, not {@code null}
     * @param <PK>   type of entity primary key
     * @param <E>    type of entity
     * @return entity state or {@code null} if not found
     */
    <PK extends Serializable, E extends Entity<PK>>
    E read(E entity);

    /**
     * Reads entities states from repository. It will read entities states by their primary keys.
     *
     * @param entities entities, not {@code null}
     * @param <PK>     type of entity primary key
     * @param <E>      type of entity
     * @return entities states mapped by primary keys or {@code null}s for those that are not found
     */
    <PK extends Serializable, E extends Entity<PK>>
    Map<PK, E> readAll(E... entities);

    /**
     * Reads entities states from repository. It will read entities states by their primary keys.
     *
     * @param entities entities, not {@code null}
     * @param <PK>     type of entity primary key
     * @param <E>      type of entity
     * @return entities states mapped by primary keys or {@code null}s for those that are not found
     */
    <PK extends Serializable, E extends Entity<PK>>
    Map<PK, E> readAll(Iterable<E> entities);

    /**
     * Reads entity state from repository by its primary key.
     *
     * @param entityClass entity class, not {@code null}
     * @param key         entity primary key, not {@code null}
     * @param <PK>        type of entity primary key
     * @param <E>         type of entity
     * @return entity state or {@code null} if not found
     */
    <PK extends Serializable, E extends Entity<PK>>
    E read(Class<E> entityClass, PK key);

    /**
     * Reads entities states from repository by their primary keys.
     *
     * @param entityClass entity class, not {@code null}
     * @param keys        entities primary keys, not {@code null}
     * @param <PK>        type of entity primary key
     * @param <E>         type of entity
     * @return entities states mapped by primary keys or {@code null}s for those that are not found
     */
    <PK extends Serializable, E extends Entity<PK>>
    Map<PK, E> readAll(Class<E> entityClass, PK... keys);

    /**
     * Reads entities states from repository by their primary keys.
     *
     * @param entityClass entity class, not {@code null}
     * @param keys        entities primary keys, not {@code null}
     * @param <PK>        type of entity primary key
     * @param <E>         type of entity
     * @return entities states mapped by primary keys or {@code null}s for those that are not found
     */
    <PK extends Serializable, E extends Entity<PK>>
    Map<PK, E> readAll(Class<E> entityClass, Iterable<PK> keys);

    /**
     * Creates new entity state in repository.
     *
     * @param entity entity, not {@code null}
     * @param <PK>   type of entity primary key
     * @param <E>    type of entity
     */
    <PK extends Serializable, E extends Entity<PK>>
    void create(E entity);

    /**
     * Creates new entities states in repository.
     *
     * @param entities entities, not {@code null}
     * @param <PK>     type of entity primary key
     * @param <E>      type of entity
     */
    <PK extends Serializable, E extends Entity<PK>>
    void createAll(E... entities);

    /**
     * Creates new entities states in repository.
     *
     * @param entities entities, not {@code null}
     * @param <PK>     type of entity primary key
     * @param <E>      type of entity
     */
    <PK extends Serializable, E extends Entity<PK>>
    void createAll(Iterable<E> entities);

    /**
     * Updates entity state in repository.
     *
     * @param entity entity, not {@code null}
     * @param <PK>   type of entity primary key
     * @param <E>    type of entity
     */
    <PK extends Serializable, E extends Entity<PK>>
    void update(E entity);

    /**
     * Updates entities states in repository.
     *
     * @param entities entities, not {@code null}
     * @param <PK>     type of entity primary key
     * @param <E>      type of entity
     */
    <PK extends Serializable, E extends Entity<PK>>
    void updateAll(E... entities);

    /**
     * Updates entities states in repository.
     *
     * @param entities entities, not {@code null}
     * @param <PK>     type of entity primary key
     * @param <E>      type of entity
     */
    <PK extends Serializable, E extends Entity<PK>>
    void updateAll(Iterable<E> entities);

    /**
     * Deletes entity state from repository.
     *
     * @param entity entity, not {@code null}
     * @param <PK>   type of entity primary key
     * @param <E>    type of entity
     */
    <PK extends Serializable, E extends Entity<PK>>
    void delete(E entity);

    /**
     * Deletes entities states from repository.
     *
     * @param entities entities, not {@code null}
     * @param <PK>     type of entity primary key
     * @param <E>      type of entity
     */
    <PK extends Serializable, E extends Entity<PK>>
    void deleteAll(E... entities);

    /**
     * Deletes entities states from repository.
     *
     * @param entities entities, not {@code null}
     * @param <PK>     type of entity primary key
     * @param <E>      type of entity
     */
    <PK extends Serializable, E extends Entity<PK>>
    void deleteAll(Iterable<E> entities);

    /**
     * Deletes entity state from repository by its primary key.
     *
     * @param entityClass entity class, not {@code null}
     * @param key         entity primary key, not {@code null}
     * @param <PK>        type of entity primary key
     * @param <E>         type of entity
     */
    <PK extends Serializable, E extends Entity<PK>>
    void delete(Class<E> entityClass, PK key);

    /**
     * Deletes entities states from repository by their primary keys.
     *
     * @param entityClass entity class, not {@code null}
     * @param keys        entities primary keys, not {@code null}
     * @param <PK>        type of entity primary key
     * @param <E>         type of entity
     */
    <PK extends Serializable, E extends Entity<PK>>
    void deleteAll(Class<E> entityClass, PK... keys);

    /**
     * Deletes entities states from repository by their primary keys.
     *
     * @param entityClass entity class, not {@code null}
     * @param keys        entities primary keys, not {@code null}
     * @param <PK>        type of entity primary key
     * @param <E>         type of entity
     */
    <PK extends Serializable, E extends Entity<PK>>
    void deleteAll(Class<E> entityClass, Iterable<PK> keys);

    /**
     * Creates new entity state if it is new or updates entity state in repository otherwise.
     *
     * @param entity entity, not {@code null}
     * @param <PK>   type of entity primary key
     * @param <E>    type of entity
     */
    <PK extends Serializable, E extends Entity<PK>>
    void save(E entity);

    /**
     * Creates new entity state if it is new or updates entity state in repository otherwise.
     *
     * @param entities entities, not {@code null}
     * @param <PK>     type of entity primary key
     * @param <E>      type of entity
     */
    <PK extends Serializable, E extends Entity<PK>>
    void saveAll(E... entities);

    /**
     * Creates new entity state if it is new or updates entity state in repository otherwise.
     *
     * @param entities entities, not {@code null}
     * @param <PK>     type of entity primary key
     * @param <E>      type of entity
     */
    <PK extends Serializable, E extends Entity<PK>>
    void saveAll(Iterable<E> entities);

    <PK extends Serializable, E extends Entity<PK>>
    Query<PK, E> query(Class<E> entityClass);
}
