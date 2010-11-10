/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.scope;

import org.greatage.ioc.ServiceResources;
import org.greatage.ioc.proxy.ObjectBuilder;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Scope {

	<E> E get(ServiceResources<E> resources, ObjectBuilder<E> builder);

}
