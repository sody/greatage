/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.proxy;

/**
 * This class represent method advice that is used to advice object proxy methods with some additional functionality.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface MethodAdvice {

	/**
	 * Advices method invocation. It can override method or add some additional functionality to method.
	 *
	 * @param invocation method invocation
	 * @param parameters invocation parameters
	 * @return method return result
	 * @throws Throwable when error occurs during invocation or may be added by this method advice
	 */
	Object advice(Invocation invocation, Object... parameters) throws Throwable;
}
