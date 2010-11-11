/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Service<T> {

	String getServiceId();

	Class<T> getServiceClass();

	boolean isOverride();

	String getScope();

	T build(ServiceResources<T> resources);

}
