/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import org.greatage.ioc.annotations.Decorate;
import org.greatage.ioc.annotations.Order;
import org.greatage.ioc.logging.Logger;
import org.greatage.util.CollectionUtils;
import org.greatage.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents default implementation of service decorator definition that distributively decorates services.
 * It is based on decorating service by invoking module method.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.0
 */
public class DecoratorImpl<T> implements Decorator<T> {
	private final Class<?> moduleClass;
	private final Method decorateMethod;

	private final Class<T> serviceClass;
	private final String serviceId;

	private final String orderId;
	private final List<String> orderConstraints;

	/**
	 * Creates new instance of service decorator definition with defined module class and method used for service
	 * decoration. Decoration method must have return and first argument types equal to service type and be annotated with
	 * {@link Decorate} annotation. For decorators ordering it may also be annotated with {@link Order} annotation.
	 *
	 * @param moduleClass	module class
	 * @param decorateMethod module method used for service decoration
	 */
	DecoratorImpl(final Class<?> moduleClass, final Method decorateMethod) {
		this.moduleClass = moduleClass;
		this.decorateMethod = decorateMethod;

		//noinspection unchecked
		serviceClass = (Class<T>) decorateMethod.getReturnType();
		if (!serviceClass.equals(decorateMethod.getParameterTypes()[0])) {
			throw new IllegalArgumentException("Decorate method must have equals return type and first argument type");
		}

		final Decorate decorate = decorateMethod.getAnnotation(Decorate.class);
		serviceId = !StringUtils.isEmpty(decorate.serviceId()) ? decorate.serviceId() : null;

		final Order order = decorateMethod.getAnnotation(Order.class);
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
	 * {@inheritDoc} It decorates service by invoking configured module method.
	 */
	public T decorate(final ServiceResources<T> resources) {
		final Logger logger = resources.getResource(Logger.class);
		logger.info("Decorating service (%s, %s) from module (%s, %s)", resources.getServiceId(),
				resources.getServiceClass(), moduleClass, decorateMethod);

		try {
			final Class<T> resultClass = resources.getServiceClass();
			final Object moduleInstance =
					Modifier.isStatic(decorateMethod.getModifiers()) ? null : resources.getResource(moduleClass);
			final Object[] parameters = InternalUtils.calculateParameters(resources, decorateMethod);
			return resultClass.cast(decorateMethod.invoke(moduleInstance, parameters));
		} catch (Exception e) {
			throw new RuntimeException(
					String.format("Can't create service configuration with id '%s'", resources.getServiceId()), e);
		}
	}
}