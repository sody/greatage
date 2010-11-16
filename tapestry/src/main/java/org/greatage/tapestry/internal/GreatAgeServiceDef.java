/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.internal;

import org.apache.tapestry5.ioc.ObjectCreator;
import org.apache.tapestry5.ioc.ScopeConstants;
import org.apache.tapestry5.ioc.ServiceBuilderResources;
import org.apache.tapestry5.ioc.def.ServiceDef;
import org.greatage.ioc.ServiceStatus;

import java.util.Collections;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GreatAgeServiceDef implements ServiceDef {
	private final ServiceStatus service;

	public GreatAgeServiceDef(final ServiceStatus service) {
		this.service = service;
	}

	public ObjectCreator createServiceCreator(final ServiceBuilderResources resources) {
		return new ObjectCreator() {
			public Object createObject() {
				return service.getService();
			}
		};
	}

	public String getServiceId() {
		return service.getServiceId();
	}

	public Set<Class> getMarkers() {
		return Collections.emptySet();
	}

	public Class getServiceInterface() {
		return service.getServiceClass();
	}

	public String getServiceScope() {
		return ScopeConstants.DEFAULT;
	}

	public boolean isEagerLoad() {
		return false;
	}
}
