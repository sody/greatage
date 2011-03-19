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
