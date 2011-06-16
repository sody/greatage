package org.greatage.ioc.internal;

import org.greatage.ioc.Interceptor;
import org.greatage.ioc.Invocation;
import org.greatage.ioc.InvocationFilter;
import org.greatage.ioc.ServiceAdviceOptions;
import org.greatage.util.AnnotationFactory;
import org.greatage.util.Ordered;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceAdviceOptionsImpl implements ServiceAdviceOptions, Ordered {
	private static final InvocationFilter DEFAULT_FILTER = new InvocationFilter() {
		public boolean supports(final Invocation invocation) {
			return true;
		}
	};

	private final Interceptor interceptor;

	private final String orderId;
	private final List<String> orderConstraints;

	private Annotation annotation;

	ServiceAdviceOptionsImpl(final Interceptor interceptor, final String orderId, final String... orderConstraints) {
		this.interceptor = interceptor;
		this.orderId = orderId;
		this.orderConstraints = Arrays.asList(orderConstraints);
	}

	public String getOrderId() {
		return orderId;
	}

	public List<String> getOrderConstraints() {
		return orderConstraints;
	}

	public ServiceAdviceOptions annotatedWith(final Annotation annotation) {
		this.annotation = annotation;
		return this;
	}

	public ServiceAdviceOptions annotatedWith(final Class<? extends Annotation> annotationClass) {
		this.annotation = AnnotationFactory.create(annotationClass);
		return this;
	}

	public InterceptorHolder build() {
		return new InterceptorHolder(interceptor, annotation);
	}
}
