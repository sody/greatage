package org.greatage.ioc.internal;

import org.greatage.ioc.Service;
import org.greatage.ioc.ServiceLocator;
import org.greatage.ioc.ServiceResources;
import org.greatage.ioc.annotations.Inject;
import org.greatage.ioc.annotations.Symbol;
import org.greatage.ioc.services.Logger;
import org.greatage.ioc.services.SymbolSource;
import org.greatage.util.StringUtils;

import java.lang.annotation.Annotation;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceInitialResources<T> implements ServiceResources<T> {
	private final ServiceLocator locator;
	private final String serviceId;
	private final Class<T> serviceClass;
	private final String serviceScope;

	ServiceInitialResources(final ServiceLocator locator, final Service<T> service) {
		this(locator, service.getServiceId(), service.getServiceClass(), service.getScope());
	}

	private ServiceInitialResources(final ServiceLocator locator, final String serviceId, final Class<T> serviceClass, final String serviceScope) {
		this.locator = locator;
		this.serviceId = serviceId;
		this.serviceClass = serviceClass;
		this.serviceScope = serviceScope;
	}

	public String getServiceId() {
		return serviceId;
	}

	public Class<T> getServiceClass() {
		return serviceClass;
	}

	public String getServiceScope() {
		return serviceScope;
	}

	public Logger getLogger() {
		return locator.getLogger();
	}

	public <E> E getResource(final Class<E> resourceClass, final Annotation... annotations) {
		final Symbol symbol = findAnnotation(annotations, Symbol.class);
		if (symbol != null) {
			if (!String.class.equals(resourceClass)) {
				throw new IllegalArgumentException("Symbol annotation can be used only with String parameters");
			}
			final SymbolSource symbolSource = getResource(SymbolSource.class);
			final String value = symbolSource.getValue(symbol.value());
			return resourceClass.cast(value);
		}

		final Inject inject = findAnnotation(annotations, Inject.class);
		if (inject != null && !StringUtils.isEmpty(inject.value())) {
			return locator.getService(inject.value(), resourceClass);
		}
		return locator.getService(resourceClass);
	}

	private static <A extends Annotation> A findAnnotation(final Annotation[] annotations, final Class<A> annotationClass) {
		for (Annotation annotation : annotations) {
			if (annotationClass.isInstance(annotation)) return annotationClass.cast(annotation);
		}
		return null;
	}
}
