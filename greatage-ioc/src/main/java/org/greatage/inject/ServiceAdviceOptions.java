package org.greatage.inject;

import java.lang.annotation.Annotation;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ServiceAdviceOptions {

	ServiceAdviceOptions annotatedWith(Annotation annotation);

	ServiceAdviceOptions annotatedWith(Class<? extends Annotation> annotationClass);

}
