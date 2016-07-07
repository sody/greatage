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

import org.greatage.domain.internal.CompositeMapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class EntityMapper<PK extends Serializable, E extends Entity<PK>> extends CompositeMapper {
    private static final String DEFAULT_ID_PROPERTY = "id";

    public final PropertyMapper<PK> id$;

    private final PropertyMapper<E> entity$;
    private final String cachedPath;

    public EntityMapper(final String path, final String property) {
        this(path, property, DEFAULT_ID_PROPERTY);
    }

    public EntityMapper(final String path, final String property, final String idProperty) {
        super(path, property);
        cachedPath = join(path, property);

        id$ = property(idProperty);
        entity$ = new PropertyMapper<E>(path, property);
    }

    public Query.Criteria isNull() {
        return equal(null);
    }

    public Query.Criteria notNull() {
        return notEqual(null);
    }

    public Query.Criteria eq(final E entity) {
        return equal(entity);
    }

    public Query.Criteria equal(final E entity) {
        final PK pk = toId(entity);
        return pk != null ? id$.equal(pk) : entity$.isNull();
    }

    public Query.Criteria ne(final E entity) {
        return notEqual(entity);
    }

    public Query.Criteria notEqual(final E entity) {
        final PK pk = toId(entity);
        return pk != null ? id$.notEqual(toId(entity)) : entity$.notNull();
    }

    public Query.Criteria in(final E... entities) {
        final List<PK> pks = new ArrayList<PK>(entities.length);
        for (E entity : entities) {
            pks.add(toId(entity));
        }
        return id$.in(pks);
    }

    public Query.Criteria in(final Collection<E> entities) {
        final List<PK> pks = new ArrayList<PK>(entities.size());
        for (E entity : entities) {
            pks.add(toId(entity));
        }
        return id$.in(pks);
    }

    public Query.Criteria nin(final E... entities) {
        return notIn(entities);
    }

    public Query.Criteria nin(final Collection<E> entities) {
        return notIn(entities);
    }

    public Query.Criteria notIn(final E... entities) {
        final List<PK> pks = new ArrayList<PK>(entities.length);
        for (E entity : entities) {
            pks.add(toId(entity));
        }
        return id$.notIn(pks);
    }

    public Query.Criteria notIn(final Collection<E> entities) {
        final List<PK> pks = new ArrayList<PK>(entities.size());
        for (E entity : entities) {
            pks.add(toId(entity));
        }
        return id$.notIn(pks);
    }

    protected String calculatePath() {
        return cachedPath;
    }

    private PK toId(final E entity) {
        return entity != null ? entity.getId() : null;
    }
}
