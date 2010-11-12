/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.hibernate;

import org.greatage.domain.*;
import org.greatage.ioc.OrderedConfiguration;
import org.greatage.ioc.ScopeConstants;
import org.greatage.ioc.ServiceBinder;
import org.greatage.ioc.annotations.*;
import org.greatage.ioc.resource.Resource;
import org.greatage.ioc.resource.ResourceLocator;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HibernateModule {

	@Bind
	public static void bind(final ServiceBinder binder) {
		binder.bind(HibernateExecutor.class, HibernateExecutorImpl.class).withScope(ScopeConstants.THREAD);
		binder.bind(EntityFilterProcessor.class, CompositeFilterProcessor.class).withId("HibernateFilterProcessor");
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
										@Inject("HibernateBaseConfiguration") final HibernateConfiguration baseConfiguration,
										@Inject("HibernatePropertyConfiguration") final HibernateConfiguration propertyConfiguration,
										@Inject("HibernateAnnotationConfiguration") final HibernateConfiguration annotationConfiguration) throws IOException {
		configuration.add(baseConfiguration, "Base");
		configuration.add(propertyConfiguration, "Property", "after:Base");
		configuration.add(annotationConfiguration, "Annotation", "after:Property");
	}

	@Build("HibernateBaseConfiguration")
	public HibernateConfiguration buildHibernateBaseConfiguration(
			@Symbol(DomainConstants.HIBERNATE_PROPERTIES) final String hibernatePropertiesResource,
			final ResourceLocator resourceLocator) {

		return new HibernateConfiguration() {
			public void configure(final Configuration configuration) {
				final Resource resource = resourceLocator.getResource(hibernatePropertiesResource);
				if (resource.exists()) {
					final Properties properties = new Properties();
					try {
						properties.load(resource.open());
						configuration.setProperties(properties);
					} catch (IOException e) {
						throw new RuntimeException("Can not find hibernate properties", e);
					}
				}
			}
		};
	}

	@Build("HibernatePropertyConfiguration")
	public HibernateConfiguration buildHibernatePropertyConfiguration(final Map<String, String> properties) {
		return new HibernateConfiguration() {
			public void configure(final Configuration configuration) {
				for (Map.Entry<String, String> entry : properties.entrySet()) {
					configuration.setProperty(entry.getKey(), entry.getValue());
				}
			}
		};
	}

	@Build("HibernateAnnotationConfiguration")
	public HibernateConfiguration buildHibernateAnnotationConfiguration(final Collection<Class> annotatedClasses) {
		return new HibernateConfiguration() {
			public void configure(final Configuration configuration) {
				for (Class annotatedClass : annotatedClasses) {
					configuration.addAnnotatedClass(annotatedClass);
				}
			}
		};
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
