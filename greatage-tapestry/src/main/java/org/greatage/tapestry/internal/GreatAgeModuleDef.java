/*
 * Copyright 2011 Ivan Khalopik
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
		return GreatAgeModuleDef.class.getName();
	}
}
