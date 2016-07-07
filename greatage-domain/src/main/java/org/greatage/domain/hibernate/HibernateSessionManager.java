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

package org.greatage.domain.hibernate;

import org.greatage.domain.internal.AbstractSessionManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HibernateSessionManager extends AbstractSessionManager<Session> {
    private final SessionFactory sessionFactory;

    public HibernateSessionManager(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    protected Session openSession() {
        return sessionFactory.openSession();
    }

    @Override
    protected void flushSession(final Session session) {
        session.flush();
    }

    @Override
    protected void closeSession(final Session session) {
        session.close();
    }
}
