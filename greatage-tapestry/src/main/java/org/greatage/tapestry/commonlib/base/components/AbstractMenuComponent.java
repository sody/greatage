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

package org.greatage.tapestry.commonlib.base.components;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.json.JSONObject;
import org.greatage.tapestry.commonlib.components.Menu;

/**
 * @author Ivan Khalopik
 */
public abstract class AbstractMenuComponent extends AbstractComponent {

	private String triggerId;
	private String menuId;

	@Component(
			parameters = {
					"overrides=overrides",
					"id=prop:menuId",
					"class=prop:menuClass"})
	private Menu menu;

	public String getMenuClass() {
		return null;
	}

	public String getTriggerId() {
		return triggerId;
	}

	public String getMenuId() {
		return menuId;
	}

	protected void setupRender() {
		triggerId = allocateClientId("trigger");
		menuId = allocateClientId("menu");
	}

	protected void afterRender() {
		getJavaScriptSupport().addInitializerCall("menu", new JSONObject("triggerId", triggerId, "menuId", menuId));
	}
}