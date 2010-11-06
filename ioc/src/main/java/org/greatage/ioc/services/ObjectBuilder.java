/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.services;

/**
 * This interface represents proxy generation data.
 *
 * @author Ivan Khalopik
 * @param <T> type of class that will be created by {@link #build()} method
 * @since 1.0
 */
public interface ObjectBuilder<T> {

	/**
	 * Gets proxy interface that will be implemented with both proxy and real object classes.
	 *
	 * @return proxy interface
	 */
	Class<T> getObjectClass();

	/**
	 * Builds real object. It is used for lazy creation of real object under the proxy.
	 *
	 * @return new instance of real object
	 */
	T build();

}
