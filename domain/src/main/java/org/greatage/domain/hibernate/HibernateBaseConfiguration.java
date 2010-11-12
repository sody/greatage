/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.hibernate;

import org.greatage.ioc.resource.Resource;
import org.greatage.ioc.resource.ResourceLocator;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HibernateBaseConfiguration implements HibernateConfiguration {
	private final ResourceLocator resourceLocator;
	private final String hibernatePropertiesResource;

	public HibernateBaseConfiguration(final ResourceLocator resourceLocator, final String hibernatePropertiesResource) {
		this.resourceLocator = resourceLocator;
		this.hibernatePropertiesResource = hibernatePropertiesResource;
	}

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
}
