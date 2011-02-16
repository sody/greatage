/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import org.greatage.ioc.scope.ScopeConstants;

/**
 * This class represents {@link ServiceStatus} implementation for {@link ServiceLocator} service. It always has hardly
 * specified service identifier, class, scope and instance.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceLocatorStatus implements ServiceStatus<ServiceLocator> {
	private final ServiceLocator locator;

	/**
	 * Creates new instance of service locator status with defined service locator.
	 *
	 * @param locator service locator
	 */
	ServiceLocatorStatus(final ServiceLocator locator) {
		this.locator = locator;
	}

	/**
	 * {@inheritDoc} Always returns <tt>ServiceLocator</tt> service identifier.
	 */
	public String getServiceId() {
		return ServiceLocator.class.getName();
	}

	/**
	 * {@inheritDoc} Always returns <tt>ServiceLocator</tt> service class.
	 */
	public Class<ServiceLocator> getServiceClass() {
		return ServiceLocator.class;
	}

	/**
	 * {@inheritDoc} Always returns <tt>global</tt> service scope.
	 */
	public String getServiceScope() {
		return ScopeConstants.GLOBAL;
	}

	/**
	 * {@inheritDoc} Always returns <tt>ServiceLocator</tt> instance.
	 */
	public ServiceLocator getService() {
		return locator;
	}
}
