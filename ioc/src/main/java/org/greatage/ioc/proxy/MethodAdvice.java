/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.proxy;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface MethodAdvice {

	Object advice(Invocation invocation, Object... parameters) throws Throwable;

}