/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

/**
 * This class represents service binding options that is returned by {@link org.greatage.ioc.ServiceBinder} to define
 * service unique id, service scope and is it overrides the existing service.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ServiceBindingOptions {

	/**
	 * Defines service unique id.
	 *
	 * @param id service unique id
	 * @return this service binding options
	 */
	ServiceBindingOptions withId(String id);

	/**
	 * Defines service scope.
	 *
	 * @param scope service scope
	 * @return this service binding options
	 */
	ServiceBindingOptions withScope(String scope);

	/**
	 * Defines option that service overrides the existing service definition.
	 *
	 * @return this service binding options
	 */
	ServiceBindingOptions override();

}
