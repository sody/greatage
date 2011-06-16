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
import org.greatage.ioc.services.Resource;
import org.greatage.ioc.services.ResourceLocator;
import org.greatage.security.AccessControlManager;
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
