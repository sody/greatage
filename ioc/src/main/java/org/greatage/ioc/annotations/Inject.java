package org.greatage.ioc.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
public @interface Inject {

	String value() default "";

}
