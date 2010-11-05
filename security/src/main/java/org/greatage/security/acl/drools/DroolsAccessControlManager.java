package org.greatage.security.acl.drools;

import org.greatage.security.acl.AccessControlList;
import org.greatage.security.acl.AccessControlManager;
import org.drools.KnowledgeBase;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class DroolsAccessControlManager implements AccessControlManager {
	private final KnowledgeBase knowledgeBase;

	public DroolsAccessControlManager(final KnowledgeBase knowledgeBase) {
		this.knowledgeBase = knowledgeBase;
	}

	public AccessControlList getAccessControlList(final Object object) {
		return new DroolsAccessControlList(knowledgeBase, object);
	}
}
