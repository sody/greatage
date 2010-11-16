/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ServiceLocator {

	Set<String> getServiceIds();

	ServiceStatus<?> getServiceStatus(String id);

	<T> T getService(String id, Class<T> serviceClass);

	<T> T getService(Class<T> serviceClass);

	<T> Set<T> findServices(Class<T> serviceClass);

}
