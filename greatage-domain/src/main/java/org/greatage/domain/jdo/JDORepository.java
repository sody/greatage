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

package org.greatage.domain.jdo;

import org.greatage.domain.Entity;
import org.greatage.domain.Query;
import org.greatage.domain.internal.AbstractQuery;
import org.greatage.domain.internal.AbstractRepository;
import org.greatage.domain.internal.SessionManager;
import org.greatage.util.DescriptionBuilder;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JDORepository extends AbstractRepository {
    private static final String COUNT_RESULT = "count(id)";

    private final SessionManager<PersistenceManager> sessionManager;

    public JDORepository(final SessionManager<PersistenceManager> sessionManager,
                         final Map<Class, Class> entityMapping) {
        super(entityMapping);
        this.sessionManager = sessionManager;
    }

    public <PK extends Serializable, E extends Entity<PK>>
    E get(final Class<E> entityClass, final PK pk) {
        return sessionManager.execute(new SessionManager.Callback<E, PersistenceManager>() {
            public E doInSession(final PersistenceManager session) throws Exception {
                try {
                    return session.getObjectById(getImplementation(entityClass), pk);
                } catch (Exception e) {
                    // todo: needs to process only needed exceptions
                    return null;
                }
            }
        });
    }

    public <PK extends Serializable, E extends Entity<PK>>
    void insert(final E entity) {
        sessionManager.execute(new SessionManager.Callback<Object, PersistenceManager>() {
            public Object doInSession(final PersistenceManager session) throws Exception {
                session.makePersistent(entity);
                return null;
            }
        });
    }

    public <PK extends Serializable, E extends Entity<PK>>
    void update(final E entity) {
        sessionManager.execute(new SessionManager.Callback<Object, PersistenceManager>() {
            public Object doInSession(final PersistenceManager session) throws Exception {
                session.refresh(entity);
                return null;
            }
        });
    }

    public <PK extends Serializable, E extends Entity<PK>>
    void remove(final E entity) {
        sessionManager.execute(new SessionManager.Callback<Object, PersistenceManager>() {
            public Object doInSession(final PersistenceManager session) throws Exception {
                session.deletePersistent(entity);
                return null;
            }
        });
    }

    public <PK extends Serializable, E extends Entity<PK>> Query<PK, E> query(final Class<E> entityClass) {
        return new JDOQuery<PK, E>(entityClass);
    }

    private <T, PK extends Serializable, E extends Entity<PK>>
    T execute(final JDOQuery<PK, E> query, final Callback<T> callback) {
        return sessionManager.execute(new SessionManager.Callback<T, PersistenceManager>() {
            public T doInSession(final PersistenceManager session) throws Exception {
                final Extent<? extends Entity> extent = session.getExtent(getImplementation(query.getEntityClass()), true);
                final javax.jdo.Query signedQuery = session.newQuery(extent);

                final JDOQueryVisitor<PK, E> visitor = new JDOQueryVisitor<PK, E>(signedQuery);
                visitor.visitQuery(query);

                return callback.doInQuery(signedQuery, visitor.getParameters());
            }
        });
    }

    private static interface Callback<T> {

        T doInQuery(javax.jdo.Query query, Map parameters);
    }

    class JDOQuery<PK extends Serializable, E extends Entity<PK>> extends AbstractQuery<PK, E> {

        private JDOQuery(final Class<E> entityClass) {
            super(entityClass);
        }

        public long count() {
            return execute(this, new Callback<Number>() {
                public Number doInQuery(final javax.jdo.Query query, final Map parameters) {
                    query.setResult(COUNT_RESULT);
                    query.setUnique(true);
                    return (Number) query.executeWithMap(parameters);
                }
            }).longValue();
        }

        public List<E> list() {
            return execute(this, new Callback<List<E>>() {
                @SuppressWarnings({"unchecked"})
                public List<E> doInQuery(final javax.jdo.Query query, final Map parameters) {
                    return (List<E>) query.executeWithMap(parameters);
                }
            });
        }

        public E unique() {
            return execute(this, new Callback<E>() {
                @SuppressWarnings({"unchecked"})
                public E doInQuery(final javax.jdo.Query query, final Map parameters) {
                    query.setUnique(true);
                    return (E) query.executeWithMap(parameters);
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
