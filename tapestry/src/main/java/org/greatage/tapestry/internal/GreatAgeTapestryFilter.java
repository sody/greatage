/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.internal;

import org.apache.tapestry5.TapestryFilter;
import org.apache.tapestry5.ioc.def.ModuleDef;

import javax.servlet.ServletContext;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GreatAgeTapestryFilter extends TapestryFilter {

	@Override
	protected ModuleDef[] provideExtraModuleDefs(final ServletContext context) {
		return new ModuleDef[]{
				new GreatAgeModuleDef(context.getInitParameter("greatage.module"))
		};
	}
}
