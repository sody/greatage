/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.internal;

import org.apache.tapestry5.ioc.ObjectCreator;
import org.apache.tapestry5.ioc.ScopeConstants;
import org.apache.tapestry5.ioc.ServiceBuilderResources;
import org.apache.tapestry5.ioc.def.ServiceDef;
import org.greatage.ioc.ServiceLocator;

import java.util.Collections;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GreatAgeServiceDef implements ServiceDef {
	private final ServiceLocator locator;
	private final String serviceId;
	private final Class<?> serviceClass;

	public GreatAgeServiceDef(final ServiceLocator locator, final String serviceId) {
		this.locator = locator;
		this.serviceId = serviceId;
		this.serviceClass = locator.getServiceClass(serviceId);
	}

	public ObjectCreator createServiceCreator(final ServiceBuilderResources resources) {
		return new ObjectCreator() {
			public Object createObject() {
				return locator.getService(serviceId, serviceClass);
			}
		};
	}

	public String getServiceId() {
		return serviceId;
	}

	public Set<Class> getMarkers() {
		return Collections.emptySet();
	}

	public Class getServiceInterface() {
		return serviceClass;
	}

	public String getServiceScope() {
		return ScopeConstants.DEFAULT;
	}

	public boolean isEagerLoad() {
		return false;
	}
}
