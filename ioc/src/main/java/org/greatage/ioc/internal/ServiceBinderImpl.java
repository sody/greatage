package org.greatage.ioc.internal;

import org.greatage.ioc.Service;
import org.greatage.ioc.ServiceBinder;
import org.greatage.ioc.ServiceBindingOptions;
import org.greatage.util.CollectionUtils;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceBinderImpl implements ServiceBinder {
	private final List<ServiceBindingOptionsImpl> services = CollectionUtils.newList();

	ServiceBinderImpl() {
	}

	public <T> ServiceBindingOptions bind(final Class<T> implementationClass) {
		return bind(implementationClass, implementationClass);
	}

	public <T> ServiceBindingOptions bind(final Class<T> serviceClass, final Class<? extends T> implementationClass) {
		final ServiceBindingOptionsImpl<T> options = new ServiceBindingOptionsImpl<T>(serviceClass, implementationClass);
		services.add(options);
		return options;
	}

	public List<Service> getServices() {
		final List<Service> result = CollectionUtils.newList();
		for (ServiceBindingOptionsImpl options : services) {
			result.add(options.createService());
		}
		return result;
	}
}
