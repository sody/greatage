/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.proxy;

import java.util.List;

/**
 * This class represents utility producing proxy creation around object builders that instantiates the actual object
 * only as needed with all configured method advices.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ProxyFactory {

	/**
	 * Creates proxy around specified object builder that instantiates the actual object only as needed with all configured
	 * method advices.
	 *
	 * @param objectBuilder object builder
	 * @param advices	   method advices
	 * @param <T>           type of proxy object
	 * @return proxy around specified object builder
	 * @throws RuntimeException if proxy creation is impossible or if an error occurs instantiating it
	 */
	<T> T createProxy(ObjectBuilder<T> objectBuilder, List<MethodAdvice> advices);
}
