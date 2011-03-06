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
