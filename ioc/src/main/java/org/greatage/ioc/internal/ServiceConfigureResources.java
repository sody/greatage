package org.greatage.ioc.internal;

import org.greatage.ioc.ServiceResources;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceConfigureResources<T> extends ServiceAdditionalResources<T> {
	private final Object configuration;

	ServiceConfigureResources(final ServiceResources<T> delegate, final Object configuration) {
		super(delegate);
		this.configuration = configuration;
	}

	@Override
	public <E> E getAdditionalResource(final Class<E> resourceClass) {
		if (resourceClass.isInstance(configuration)) {
			return resourceClass.cast(configuration);
		}
		return null;
	}
}
