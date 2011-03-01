/*
 * Copyright 2011 Ivan Khalopik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

	private final Logger logger;

	/**
	 * Creates new instance of service interceptor definition with defined module class and method used for service
	 * interception. Interception method must have {@link MethodAdvice} return type and be annotated with {@link Intercept}
	 * annotation. For interceptors ordering it may also be annotated with {@link Order} annotation.
	 *
	 * @param logger		  system logger
	 * @param moduleClass	 module class
	 * @param interceptMethod module method used for service interception
	 */
	InterceptorImpl(final Logger logger, final Class<?> moduleClass, final Method interceptMethod) {
		this.logger = logger;
		this.moduleClass = moduleClass;
		this.interceptMethod = interceptMethod;

		final Intercept intercept = interceptMethod.getAnnotation(Intercept.class);
		serviceId = InternalUtils.generateServiceId(intercept.value(), intercept.id());
		//noinspection unchecked
		serviceClass = intercept.service();

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
		logger.info("Intercepting service (%s, %s) from module (%s, %s)", resources.getServiceId(),
				resources.getServiceClass(), moduleClass, interceptMethod);

		try {
			final Object moduleInstance =
					Modifier.isStatic(interceptMethod.getModifiers()) ? null : resources.getResource(moduleClass);
			final Object[] parameters = InternalUtils.calculateParameters(resources, interceptMethod);
			return (MethodAdvice) interceptMethod.invoke(moduleInstance, parameters);
		} catch (Exception e) {
			throw new ApplicationException(
					String.format("Can't create service interceptor with id '%s'", resources.getServiceId()), e);
		}
	}
}
