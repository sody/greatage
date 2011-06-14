package org.greatage.util;

import java.lang.annotation.Annotation;
import java.lang.annotation.IncompleteAnnotationException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AnnotationFactory<A extends Annotation> implements InvocationHandler {
	private final Class<A> annotationClass;
	private final int hashCode;

	public AnnotationFactory(final Class<A> annotationClass) {
		this.annotationClass = annotationClass;
		//todo: add defining values for annotation members, include them in hashCode() and equals()
		hashCode = annotationClass.hashCode();
	}

	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
		final String member = method.getName();
		final Class[] parameterTypes = method.getParameterTypes();

		// Handle Object and Annotation methods
		if (member.equals("equals") && parameterTypes.length == 1 && parameterTypes[0] == Object.class) {
			return equalsImpl(args[0]);
		}

		assert parameterTypes.length == 0;
		if (member.equals("toString")) {
			return toString();
		}
		else if (member.equals("hashCode")) {
			return hashCode;
		}
		else if (member.equals("annotationType")) {
			return annotationClass;
		}

		final Object result = method.getDefaultValue();
		if (result == null) {
			throw new IncompleteAnnotationException(annotationClass, member);
		}
		return result;
	}

	private Boolean equalsImpl(Object o) {
		if (o == this) {
			return true;
		}
		if (!annotationClass.isInstance(o)) {
			return false;
		}
		//todo: add members equals checking
		return true;
	}

	public static <A extends Annotation> A create(final Class<A> annotationClass) {
		final Object proxy = Proxy.newProxyInstance(annotationClass.getClassLoader(),
				new Class[] { annotationClass },
				new AnnotationFactory<A>(annotationClass));
		return annotationClass.cast(proxy);
	}
}
