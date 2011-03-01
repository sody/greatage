/*
 * Copyright 2011 Ivan Khalopik
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

package org.greatage.security.drools;

import org.drools.KnowledgeBase;
import org.drools.runtime.StatelessKnowledgeSession;
import org.greatage.security.AccessControlEntry;
import org.greatage.security.AccessControlList;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class DroolsAccessControlList implements AccessControlList {
	private final KnowledgeBase knowledgeBase;
	private final Object object;

	public DroolsAccessControlList(final KnowledgeBase knowledgeBase, final Object object) {
		this.object = object;
		this.knowledgeBase = knowledgeBase;
	}

	public Object getSecuredObject() {
		return object;
	}

	public AccessControlEntry getAccessControlEntry(final String authority, final String permission) {
		final StatelessKnowledgeSession knowledgeSession = knowledgeBase.newStatelessKnowledgeSession();
		final DroolsAccessControlEntry ace = new DroolsAccessControlEntry(object, authority, permission);
		knowledgeSession.execute(ace);
		return ace;
	}

	public boolean isGranted(final String authority, final String permission) {
		final StatelessKnowledgeSession knowledgeSession = knowledgeBase.newStatelessKnowledgeSession();
		final DroolsAccessControlEntry ace = new DroolsAccessControlEntry(object, authority, permission);
		knowledgeSession.execute(ace);
		return ace.isGranted();
	}
}
