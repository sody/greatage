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

import org.greatage.domain.DomainConstants;
import org.greatage.domain.EntityRepository;
import org.greatage.inject.MappedConfiguration;
import org.greatage.inject.OrderedConfiguration;
import org.greatage.inject.ServiceBinder;
import org.greatage.inject.annotations.*;
import org.greatage.inject.services.ResourceLocator;
import org.greatage.inject.services.SymbolProvider;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HibernateModule {

	@Bind
	public static void bind(final ServiceBinder binder) {
		binder.bind(HibernateConfiguration.class, HibernateAnnotationConfiguration.class)
				.named("HibernateAnnotationConfiguration");
		binder.bind(HibernateConfiguration.class, HibernatePropertyConfiguration.class).named("HibernatePropertyConfiguration");
		binder.bind(HibernateExecutor.class, HibernateExecutorImpl.class).withScope(Threaded.class);
	}

	@Contribute(SymbolProvider.class)
	public void contributeApplicationSymbolProvider(final MappedConfiguration<String, String> configuration) {
		configuration.add(DomainConstants.HIBERNATE_PROPERTIES, "hibernate.properties");
	}

	@Build
	public SessionFactory buildSessionFactory(final List<HibernateConfiguration> configurations) {
		final org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
		for (HibernateConfiguration hibernateConfiguration : configurations) {
			hibernateConfiguration.configure(configuration);
		}
		return configuration.buildSessionFactory();
	}

	@Contribute(SessionFactory.class)
	public void contributeSessionFactory(final OrderedConfiguration<HibernateConfiguration> configuration,
										 @Named("HibernatePropertyConfiguration") final HibernateConfiguration propertyConfiguration,
										 @Named("HibernateAnnotationConfiguration") final HibernateConfiguration annotationConfiguration,
										 @Symbol(DomainConstants.HIBERNATE_PROPERTIES) final String hibernatePropertiesResource,
										 final ResourceLocator resourceLocator) throws IOException {
		configuration.add(new HibernateBaseConfiguration(resourceLocator, hibernatePropertiesResource), "Base");
		configuration.add(propertyConfiguration, "Property", "after:Base");
		configuration.add(annotationConfiguration, "Annotation", "after:Property");
	}

	@Build
	public EntityRepository buildHibernateRepository(final Map<Class, Class> aliases,
													 final HibernateExecutor executor) {
		return new HibernateRepository(executor, aliases);
	}
}
