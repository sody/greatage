/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ServiceBinder {

	<T> ServiceBindingOptions bind(Class<T> implementationClass);

	<T> ServiceBindingOptions bind(Class<T> serviceClass, Class<? extends T> implementationClass);

}
