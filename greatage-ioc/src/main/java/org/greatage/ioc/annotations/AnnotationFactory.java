package org.greatage.ioc.annotations;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class AnnotationFactory implements InvocationHandler {
	private static final AnnotationFactory ANNOTATION_HANDLER = new AnnotationFactory();

	public static <A extends Annotation> A create(final Class<A> annotationClass) {
		final Object proxy =
				Proxy.newProxyInstance(annotationClass.getClassLoader(), new Class[] { annotationClass }, ANNOTATION_HANDLER);
		return annotationClass.cast(proxy);
	}

	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
		return method.getDefaultValue();
	}
}
