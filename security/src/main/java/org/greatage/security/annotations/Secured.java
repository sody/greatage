/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.security.annotations;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public @interface Secured {

	String value() default "";

}
