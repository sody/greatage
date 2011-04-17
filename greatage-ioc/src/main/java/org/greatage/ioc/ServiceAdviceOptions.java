package org.greatage.ioc;

import java.lang.annotation.Annotation;

/**
 * @author Ivan Khalopik
 * @since 8.0
 */
public interface ServiceAdviceOptions {

	ServiceAdviceOptions annotatedWith(Annotation annotation);

	ServiceAdviceOptions annotatedWith(Class<? extends Annotation> annotationClass);

}
