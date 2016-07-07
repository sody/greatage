package org.greatage.inject.internal;

import org.greatage.inject.Interceptor;
import org.greatage.inject.Invocation;
import org.greatage.inject.InvocationFilter;
import org.greatage.inject.ServiceAdviceOptions;
import org.greatage.util.AnnotationFactory;
import org.greatage.util.Ordered;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
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

	public boolean supports(final Method method) {
		if (annotation != null) {
			final Annotation methodAnnotation = method.getAnnotation(annotation.annotationType());
			return annotation.equals(methodAnnotation);
		}
		return true;
	}

	public Interceptor getInterceptor() {
		return interceptor;
	}
}
