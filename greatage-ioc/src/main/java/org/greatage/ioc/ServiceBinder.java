/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

/**
 * This class represents service bind definition that distributly binds service interfaces to their automatically built
 * instances. It can also specify service unique id, service scope and is it overrides the existing service. By default
 * it is configured by module static bind methods annotated with {@link org.greatage.ioc.annotations.Bind} class.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ServiceBinder {

	/**
	 * Binds specified service interface to its automatically built implementation. Specified implementation class will be
	 * used as its service interface.
	 *
	 * @param implementationClass service implementation class
	 * @param <T>                 type of service
	 * @return service binding options that can be used to define service unique id, service scope and its override option
	 */
	<T> ServiceBindingOptions bind(Class<T> implementationClass);

	/**
	 * Binds specified service interface to its automatically built implementation.
	 *
	 * @param serviceClass		service interface
	 * @param implementationClass service implementation class
	 * @param <T>                 type of service
	 * @return service binding options that can be used to define service unique id, service scope and its override option
	 */
	<T> ServiceBindingOptions bind(Class<T> serviceClass, Class<? extends T> implementationClass);

}
