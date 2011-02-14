/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import org.greatage.ioc.annotations.Intercept;
import org.greatage.ioc.annotations.Order;
import org.greatage.ioc.logging.Logger;
import org.greatage.ioc.proxy.MethodAdvice;
import org.greatage.util.CollectionUtils;
import org.greatage.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents default implementation of service interceptor definition that distributively configures service
 * method advices. It is based on intercepting service by invoking module method.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.0
 */
public class InterceptorImpl<T> implements Interceptor<T> {
	private final Class<?> moduleClass;
	private final Method interceptMethod;

	private final Class<T> serviceClass;
	private final String serviceId;

	private final String orderId;
	private final List<String> orderConstraints;

	/**
	 * Creates new instance of service interceptor definition with defined module class and method used for service
	 * interception. Interception method must have {@link MethodAdvice} return type and be annotated with {@link Intercept}
	 * annotation. For interceptors ordering it may also be annotated with {@link Order} annotation.
	 *
	 * @param moduleClass	 module class
	 * @param interceptMethod module method used for service interception
	 */
	InterceptorImpl(final Class<?> moduleClass, final Method interceptMethod) {
		this.moduleClass = moduleClass;
		this.interceptMethod = interceptMethod;

		final Intercept intercept = interceptMethod.getAnnotation(Intercept.class);
		serviceId = !StringUtils.isEmpty(intercept.serviceId()) ? intercept.serviceId() : null;
		//noinspection unchecked
		serviceClass = intercept.value();

		final Order order = interceptMethod.getAnnotation(Order.class);
		if (order != null) {
			orderId = order.value();
			orderConstraints = Arrays.asList(order.constraints());
		} else {
			orderId = "";
			orderConstraints = CollectionUtils.newList();
		}
	}

	/**
	 * {@inheritDoc} This decorator supports service if its service identifier or service class correspond to configured
	 * ones.
	 */
	public boolean supports(final Service service) {
		return serviceId != null ?
				service.getServiceId().equals(serviceId) :
				serviceClass.isAssignableFrom(service.getServiceClass());
	}

	/**
	 * {@inheritDoc}
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<String> getOrderConstraints() {
		return orderConstraints;
	}

	/**
	 * {@inheritDoc} It intercepts service by invoking configured module method.
	 */
	public MethodAdvice intercept(final ServiceResources<T> resources) {
		final Logger logger = resources.getResource(Logger.class);
		logger.info("Intercepting service (%s, %s) from module (%s, %s)", resources.getServiceId(),
				resources.getServiceClass(), moduleClass, interceptMethod);

		try {
			final Object moduleInstance =
					Modifier.isStatic(interceptMethod.getModifiers()) ? null : resources.getResource(moduleClass);
			final Object[] parameters = InternalUtils.calculateParameters(resources, interceptMethod);
			return (MethodAdvice) interceptMethod.invoke(moduleInstance, parameters);
		} catch (Exception e) {
			throw new RuntimeException(
					String.format("Can't create service interceptor with id '%s'", resources.getServiceId()), e);
		}
	}
}
