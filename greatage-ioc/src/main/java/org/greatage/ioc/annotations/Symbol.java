/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation marks class fields and method parameters inside the IoC container as injected symbols with specified
 * expression provided by {@link org.greatage.ioc.symbol.SymbolSource}.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Symbol {

	/**
	 * This property defines symbol expression to inject.
	 */
	String value();
}
