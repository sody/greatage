/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ServiceStatus<T> {

	String getServiceId();

	Class<T> getServiceClass();

	String getServiceScope();

	T getService();

}
