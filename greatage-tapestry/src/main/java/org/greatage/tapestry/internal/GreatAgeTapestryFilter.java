/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.greatage.tapestry.internal;

import org.apache.tapestry5.TapestryFilter;
import org.apache.tapestry5.ioc.def.ModuleDef;
import org.greatage.ioc.tapestry.GreatAgeIntegration;

import javax.servlet.ServletContext;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GreatAgeTapestryFilter extends TapestryFilter {
	private static final String GREATAGE_MODULE_PARAMETER = "greatage.module";

	@Override
	protected ModuleDef[] provideExtraModuleDefs(final ServletContext context) {
		try {
			final String moduleName = context.getInitParameter(GREATAGE_MODULE_PARAMETER);
			final Class<?> moduleClass = Class.forName(moduleName);

			return new ModuleDef[]{new GreatAgeIntegration(moduleClass)};
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't find module class", e);
		}
	}
}
