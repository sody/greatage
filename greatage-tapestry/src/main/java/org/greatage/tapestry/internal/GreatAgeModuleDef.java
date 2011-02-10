/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.internal;

import org.apache.tapestry5.ioc.def.ContributionDef;
import org.apache.tapestry5.ioc.def.DecoratorDef;
import org.apache.tapestry5.ioc.def.ModuleDef;
import org.apache.tapestry5.ioc.def.ServiceDef;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.greatage.ioc.ServiceLocator;
import org.greatage.ioc.ServiceLocatorBuilder;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GreatAgeModuleDef implements ModuleDef {
	private final Map<String, ServiceDef> services = CollectionFactory.newMap();

	public GreatAgeModuleDef(final String moduleName) {
		try {
			final Class<?> moduleClass;
			moduleClass = Class.forName(moduleName);
			final ServiceLocator locator = ServiceLocatorBuilder.createServiceLocator(moduleClass);
			for (String serviceId : locator.getServiceIds()) {
				services.put(serviceId, new GreatAgeServiceDef(locator.getServiceStatus(serviceId)));
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't find module class", e);
		}
	}

	public Set<String> getServiceIds() {
		return services.keySet();
	}

	public ServiceDef getServiceDef(final String serviceId) {
		return services.get(serviceId);
	}

	public Set<DecoratorDef> getDecoratorDefs() {
		return Collections.emptySet();
	}

	public Set<ContributionDef> getContributionDefs() {
		return Collections.emptySet();
	}

	public Class getBuilderClass() {
		return null;
	}

	public String getLoggerName() {
		return GreatAgeModuleDef.class.getSimpleName();
	}
}
