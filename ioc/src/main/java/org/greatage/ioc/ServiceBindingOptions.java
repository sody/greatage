/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ServiceBindingOptions {

	ServiceBindingOptions withId(String id);

	ServiceBindingOptions withScope(String scope);

	ServiceBindingOptions override();

}
