/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.hibernate;

import org.greatage.domain.*;
import org.greatage.ioc.MappedConfiguration;
import org.greatage.ioc.OrderedConfiguration;
import org.greatage.ioc.ScopeConstants;
import org.greatage.ioc.ServiceBinder;
import org.greatage.ioc.annotations.*;
import org.greatage.ioc.resource.ResourceLocator;
import org.greatage.ioc.symbol.SymbolProvider;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
		binder.bind(HibernateConfiguration.class, HibernateAnnotationConfiguration.class).withId("HibernateAnnotationConfiguration");
		binder.bind(HibernateConfiguration.class, HibernatePropertyConfiguration.class).withId("HibernatePropertyConfiguration");
		binder.bind(HibernateExecutor.class, HibernateExecutorImpl.class).withScope(ScopeConstants.THREAD);
		binder.bind(EntityFilterProcessor.class, CompositeFilterProcessor.class).withId("HibernateFilterProcessor");
	}

	@Configure(value = SymbolProvider.class, serviceId = "ApplicationSymbolProvider")
	public void configureApplicationSymbolProvider(final MappedConfiguration<String, String> configuration) {
		configuration.add(DomainConstants.HIBERNATE_PROPERTIES, "hibernate.properties");
	}

	@Build
	public SessionFactory buildSessionFactory(final List<HibernateConfiguration> configurations) {
		final Configuration configuration = new Configuration();
		for (HibernateConfiguration hibernateConfiguration : configurations) {
			hibernateConfiguration.configure(configuration);
		}
		return configuration.buildSessionFactory();
	}

	@Configure(SessionFactory.class)
	public void configureSessionFactory(final OrderedConfiguration<HibernateConfiguration> configuration,
										@Inject("HibernatePropertyConfiguration") final HibernateConfiguration propertyConfiguration,
										@Inject("HibernateAnnotationConfiguration") final HibernateConfiguration annotationConfiguration,
										@Symbol(DomainConstants.HIBERNATE_PROPERTIES) final String hibernatePropertiesResource,
										final ResourceLocator resourceLocator) throws IOException {
		configuration.add(new HibernateBaseConfiguration(resourceLocator, hibernatePropertiesResource), "Base");
		configuration.add(propertyConfiguration, "Property", "after:Base");
		configuration.add(annotationConfiguration, "Annotation", "after:Property");
	}

	@Build("HibernateRepository")
	public EntityRepository buildHibernateRepository(final Map<Class, Class> aliases,
													 final HibernateExecutor executor,
													 @Inject("HibernateFilterProcessor") final EntityFilterProcessor processor) {
		return new HibernateRepository(aliases, executor, processor);
	}

	@Configure(value = EntityFilterProcessor.class, serviceId = "HibernateFilterProcessor")
	public void configureHibernateFilterProcessor(final org.greatage.ioc.Configuration<EntityFilterProcessor> configuration) {
		configuration.addInstance(BaseFilterProcessor.class);
	}

}
