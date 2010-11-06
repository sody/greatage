/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import org.greatage.ioc.services.Logger;

import java.lang.annotation.Annotation;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ServiceResources<T> {

	String getServiceId();

	Class<T> getServiceClass();

	String getServiceScope();

	Logger getLogger();

	<E> E getResource(Class<E> resourceClass, Annotation... annotations);

}
