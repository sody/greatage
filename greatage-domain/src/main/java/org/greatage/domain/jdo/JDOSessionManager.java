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

import org.greatage.domain.internal.AbstractSessionManager;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JDOSessionManager extends AbstractSessionManager<PersistenceManager> {
	private final PersistenceManagerFactory persistenceManagerFactory;

	public JDOSessionManager(final PersistenceManagerFactory persistenceManagerFactory) {
		this.persistenceManagerFactory = persistenceManagerFactory;
	}

	@Override
	protected PersistenceManager openSession() {
		return persistenceManagerFactory.getPersistenceManager();
	}

	@Override
	protected void flushSession(final PersistenceManager session) {
		session.flush();
	}

	@Override
	protected void closeSession(final PersistenceManager session) {
		session.close();
	}
}
