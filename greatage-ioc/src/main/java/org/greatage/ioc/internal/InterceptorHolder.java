package org.greatage.ioc.internal;

import org.greatage.ioc.Interceptor;
import org.greatage.ioc.Invocation;
import org.greatage.ioc.InvocationFilter;

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
