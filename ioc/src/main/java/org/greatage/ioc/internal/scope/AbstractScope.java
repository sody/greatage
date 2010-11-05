package org.greatage.ioc.internal.scope;

import org.greatage.ioc.ServiceResources;
import org.greatage.ioc.services.ObjectBuilder;
import org.greatage.ioc.services.Scope;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractScope implements Scope {

	public <E> E get(final ServiceResources<E> resources, final ObjectBuilder<E> builder) {
		if (!contains(resources)) {
			final E service = builder.build();
			put(resources, service);
		}
		return get(resources);
	}

	protected abstract <E> boolean contains(final ServiceResources<E> resources);

	protected abstract <E> E get(final ServiceResources<E> resources);

	protected abstract <E> void put(final ServiceResources<E> resources, E service);
}
