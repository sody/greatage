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

package org.greatage.domain.jpa;

import org.greatage.domain.Entity;
import org.greatage.domain.Query;
import org.greatage.domain.internal.AbstractQuery;
import org.greatage.domain.internal.AbstractRepository;
import org.greatage.domain.internal.SessionManager;
import org.greatage.util.DescriptionBuilder;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JPARepository extends AbstractRepository {
    private final SessionManager<EntityManager> sessionManager;

    public JPARepository(final SessionManager<EntityManager> sessionManager,
                         final Map<Class, Class> entityMapping) {
        super(entityMapping);
        this.sessionManager = sessionManager;
    }

    public <PK extends Serializable, E extends Entity<PK>>
    E read(final Class<E> entityClass, final PK key) {
        return sessionManager.execute(new SessionManager.Callback<E, EntityManager>() {
            public E doInSession(final EntityManager session) throws Exception {
                return session.find(getImplementation(entityClass), key);
            }
        });
    }

    public <PK extends Serializable, E extends Entity<PK>>
    void create(final E entity) {
        sessionManager.execute(new SessionManager.Callback<Object, EntityManager>() {
            public Object doInSession(final EntityManager session) throws Exception {
                session.persist(entity);
                return null;
            }
        });
    }

    public <PK extends Serializable, E extends Entity<PK>>
    void update(final E entity) {
        sessionManager.execute(new SessionManager.Callback<Object, EntityManager>() {
            public Object doInSession(final EntityManager session) throws Exception {
                session.merge(entity);
                return null;
            }
        });
    }

    public <PK extends Serializable, E extends Entity<PK>>
    void delete(final E entity) {
        sessionManager.execute(new SessionManager.Callback<Object, EntityManager>() {
            public Object doInSession(final EntityManager session) throws Exception {
                session.remove(entity);
                return null;
            }
        });
    }

    public <PK extends Serializable, E extends Entity<PK>> Query<PK, E> query(final Class<E> entityClass) {
        return new JPAQuery<PK, E>(entityClass);
    }

    protected <T, PK extends Serializable, E extends Entity<PK>>
    T execute(final String queryString, final JPAQuery<PK, E> query, final Callback<T> callback) {
        return sessionManager.execute(new SessionManager.Callback<T, EntityManager>() {
            public T doInSession(final EntityManager session) throws Exception {
                final javax.persistence.Query signedQuery =
                        session.createQuery(queryString.replaceAll("entityClass", query.getEntityClass().getName()));
                new JPAQueryVisitor<PK, E>(signedQuery).visitQuery(query);

                return callback.doInQuery(signedQuery);
            }
        });
    }

    public interface Callback<T> {

        T doInQuery(javax.persistence.Query query);
    }

    class JPAQuery<PK extends Serializable, E extends Entity<PK>> extends AbstractQuery<PK, E> {

        private JPAQuery(final Class<E> entityClass) {
            super(entityClass);
        }

        public long count() {
            return execute("select count() from entityClass", this, new Callback<Number>() {
                public Number doInQuery(final javax.persistence.Query query) {
                    return (Number) query.getSingleResult();
                }
            }).longValue();
        }

        public List<E> list() {
            return execute("select from entityClass", this, new Callback<List<E>>() {
                @SuppressWarnings({"unchecked"})
                public List<E> doInQuery(final javax.persistence.Query query) {
                    return query.getResultList();
                }
            });
        }

        public E unique() {
            return execute("select from entityClass", this, new Callback<E>() {
                @SuppressWarnings({"unchecked"})
                public E doInQuery(final javax.persistence.Query query) {
                    return (E) query.getSingleResult();
                }
            });
        }

        public List<PK> keys() {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public String toString() {
        final DescriptionBuilder builder = new DescriptionBuilder(getClass());
        builder.append("sessionManager", sessionManager);
        return builder.toString();
    }
}
