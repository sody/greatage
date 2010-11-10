/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
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