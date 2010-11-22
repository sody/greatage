/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * This annotation marks class fields and method parameters inside the IoC container as injected symbols with specified
 * expression provided by {@link org.greatage.ioc.symbol.SymbolSource}.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
public @interface Symbol {

	/**
	 * This property defines symbol expression to inject.
	 *
	 * @return symbol expression
	 */
	String value();

}
