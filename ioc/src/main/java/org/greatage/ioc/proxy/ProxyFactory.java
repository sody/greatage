/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.proxy;

import java.util.List;

/**
 * This class represents utility producing proxy creation around object builders.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ProxyFactory {

	/**
	 * Creates proxy around specified object builder. All method calls of proxy will be delegated to lazy initialized real
	 * object.
	 *
	 * @param objectBuilder object builder
	 * @param advices	   method advices
	 * @param <T>           type of proxy object
	 * @return proxy around specified object builder
	 * @throws RuntimeException if proxy creation is impossible
	 */
	<T> T createProxy(ObjectBuilder<T> objectBuilder, List<MethodAdvice> advices);

}
