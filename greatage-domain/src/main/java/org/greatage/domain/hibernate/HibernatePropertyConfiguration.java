/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.hibernate;

import org.hibernate.cfg.Configuration;

import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HibernatePropertyConfiguration implements HibernateConfiguration {
	private final Map<String, String> properties;

	public HibernatePropertyConfiguration(final Map<String, String> properties) {
		this.properties = properties;
	}

	public void configure(final Configuration configuration) {
		for (Map.Entry<String, String> entry : properties.entrySet()) {
			configuration.setProperty(entry.getKey(), entry.getValue());
		}
	}
}
