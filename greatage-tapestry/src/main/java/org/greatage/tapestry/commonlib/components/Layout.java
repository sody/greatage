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

package org.greatage.tapestry.commonlib.components;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.annotations.Value;
import org.apache.tapestry5.services.AssetSource;
import org.greatage.tapestry.CommonSymbols;

/**
 * @author Ivan Khalopik
 */
@Import(stylesheet = {
		"layout.css",
		"reset.css",
		"base.css",
		"fonts.css"
})
public class Layout {

	@Parameter(defaultPrefix = BindingConstants.LITERAL, allowNull = false)
	private String theme;

	@Inject
	private RenderSupport renderSupport;

	@Inject
	@Symbol(CommonSymbols.THEME)
	private String defaultTheme;

	@Inject
	@Value("classpath:${" + CommonSymbols.THEME_PATH + "}")
	private String themePath;

	@Inject
	private AssetSource assetSource;

	protected String defaultTheme() {
		return defaultTheme;
	}

	protected void afterRender() {
		final Asset themeAsset = assetSource.getAsset(null, themePath + "/" + theme + ".css", null);
		renderSupport.addStylesheetLink(themeAsset, null);
	}
}
