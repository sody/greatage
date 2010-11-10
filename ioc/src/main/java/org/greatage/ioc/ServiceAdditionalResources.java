/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import org.greatage.ioc.logging.Logger;

import java.lang.annotation.Annotation;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class ServiceAdditionalResources<T> implements ServiceResources<T> {
	private final ServiceResources<T> delegate;

	ServiceAdditionalResources(final ServiceResources<T> delegate) {
		this.delegate = delegate;
	}

	protected ServiceResources<T> getDelegate() {
		return delegate;
	}

	public String getServiceId() {
		return delegate.getServiceId();
	}

	public Class<T> getServiceClass() {
		return delegate.getServiceClass();
	}

	public String getServiceScope() {
		return delegate.getServiceScope();
	}

	public Logger getLogger() {
		return delegate.getLogger();
	}

	public <E> E getResource(final Class<E> resourceClass, final Annotation... annotations) {
		final E resource = getAdditionalResource(resourceClass);
		return resource != null ? resource : delegate.getResource(resourceClass, annotations);
	}

	protected abstract <E> E getAdditionalResource(final Class<E> resourceClass);
}
