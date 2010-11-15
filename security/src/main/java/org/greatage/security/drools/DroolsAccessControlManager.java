/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.security.drools;

import org.drools.KnowledgeBase;
import org.greatage.security.AccessControlList;
import org.greatage.security.AccessControlManager;

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
