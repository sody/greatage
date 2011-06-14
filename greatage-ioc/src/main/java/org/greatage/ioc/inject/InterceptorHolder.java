package org.greatage.ioc.inject;

import org.greatage.ioc.InvocationFilter;
import org.greatage.ioc.proxy.Interceptor;
import org.greatage.ioc.proxy.Invocation;

import java.lang.annotation.Annotation;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class InterceptorHolder implements InvocationFilter {
	private final Interceptor interceptor;
	private final Annotation annotation;

	public InterceptorHolder(final Interceptor interceptor, final Annotation annotation) {
		this.interceptor = interceptor;
		this.annotation = annotation;
	}

	public boolean supports(final Invocation invocation) {
		if (annotation != null) {
			final Annotation methodAnnotation = invocation.getMethod().getAnnotation(annotation.annotationType());
			return annotation.equals(methodAnnotation);
		}
		return true;
	}

	public Interceptor getInterceptor() {
		return interceptor;
	}
}
