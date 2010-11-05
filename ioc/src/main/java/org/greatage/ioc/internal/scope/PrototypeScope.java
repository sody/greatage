package org.greatage.ioc.internal.scope;

import org.greatage.ioc.ServiceResources;
import org.greatage.ioc.services.ObjectBuilder;
import org.greatage.ioc.services.Scope;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class PrototypeScope implements Scope {
	public <E> E get(final ServiceResources<E> resources, final ObjectBuilder<E> builder) {
		return builder.build();
	}
}
