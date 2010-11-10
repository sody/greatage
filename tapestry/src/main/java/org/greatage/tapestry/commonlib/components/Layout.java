/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
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
