/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.security.acl.drools;

import org.drools.KnowledgeBase;
import org.drools.runtime.StatelessKnowledgeSession;
import org.greatage.security.acl.AccessControlEntry;
import org.greatage.security.acl.AccessControlList;

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
