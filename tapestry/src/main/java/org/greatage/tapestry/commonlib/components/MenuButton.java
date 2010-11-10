/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.commonlib.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.corelib.components.Any;
import org.greatage.tapestry.CSSConstants;
import org.greatage.tapestry.commonlib.base.components.AbstractMenuComponent;

/**
 * @author Ivan Khalopik
 */
@Import(library = "menu.js")
@SupportsInformalParameters
public class MenuButton extends AbstractMenuComponent {

	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
	private String label;

	@Component(
			parameters = {
					"id=prop:triggerId",
					"class=prop:menuButtonClass"},
			inheritInformalParameters = true)
	private Any menuButton;

	@Override
	public String getMenuClass() {
		return CSSConstants.POPUP_MENU_CLASS;
	}

	public String getMenuButtonClass() {
		return CSSConstants.MENU_BUTTON_CLASS;
	}

	public String getLabel() {
		return label;
	}
}
