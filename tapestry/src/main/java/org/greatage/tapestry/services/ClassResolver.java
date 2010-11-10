/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.services;

/**
 * @author Ivan Khalopik
 */
public interface ClassResolver {

	<T> Class<T> getRealClass(Class<T> clazz);

}
