/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.security.drools;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.greatage.ioc.ServiceBinder;
import org.greatage.ioc.annotations.Bind;
import org.greatage.ioc.annotations.Build;
import org.greatage.ioc.annotations.Dependency;
import org.greatage.ioc.resource.Resource;
import org.greatage.ioc.resource.ResourceLocator;
import org.greatage.security.AccessControlManager;
import org.greatage.security.PermissionSecurityChecker;
import org.greatage.security.SecurityChecker;
import org.greatage.security.SecurityModule;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Dependency(SecurityModule.class)
public class DroolsSecurityModule {

	@Bind
	public static void bind(final ServiceBinder binder) {
		binder.bind(SecurityChecker.class, PermissionSecurityChecker.class).withId("PermissionSecurityChecker");
		binder.bind(AccessControlManager.class, DroolsAccessControlManager.class);
	}

	@Build
	public KnowledgeBase buildKnowledgeBase(
			final Collection<String> resources,
			final ResourceLocator resourceLocator) throws IOException {
		final KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();

		for (String resource : resources) {
			final Resource res = resourceLocator.getResource(resource);
			builder.add(ResourceFactory.newInputStreamResource(res.open()), ResourceType.DRL);
		}

		if (builder.hasErrors()) {
			final StringBuilder sb = new StringBuilder();
			for (KnowledgeBuilderError error : builder.getErrors()) {
				sb.append("Error at lines ").append(Arrays.toString(error.getErrorLines())).append(":").append("\n");
				sb.append(error.getMessage()).append("\n");
			}
			throw new RuntimeException(sb.toString());
		}
		final KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
		knowledgeBase.addKnowledgePackages(builder.getKnowledgePackages());
		return knowledgeBase;
	}
}
