/*
 * Copyright 2011 Ivan Khalopik
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
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentAction;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.corelib.components.Any;
import org.apache.tapestry5.internal.TapestryInternalUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.greatage.tapestry.CSSConstants;
import org.greatage.tapestry.commonlib.base.components.AbstractComponent;
import org.greatage.tapestry.menu.MenuModel;
import org.greatage.tapestry.services.MenuModelSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Ivan Khalopik
 */
@SupportsInformalParameters
public class Menu extends AbstractComponent {
	private static final String ACTIONS_DELIMITER = "\\s*,\\s*";

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String actions;

	@Parameter(name = "class", defaultPrefix = BindingConstants.LITERAL)
	private String className;

	@Parameter
	private Object[] context;

	private MenuModel menuModel;

	private String action;

	@Inject
	private MenuModelSource modelSource;

	@Inject
	private Block defaultBody;

	@Component(
			parameters = "class=prop:menuClass",
			inheritInformalParameters = true)
	private Any border;

	@Component(
			parameters = {
					"overrides=overrides",
					"name=prop:action"})
	private MenuItem menuItem;

	public String getMenuClass() {
		final List<String> classes = CollectionFactory.newList();
		classes.add(CSSConstants.CONTAINER_CLASS);
		classes.add(CSSConstants.MENU_CLASS);
		if (className != null) {
			classes.add(className);
		}
		return TapestryInternalUtils.toClassAttributeValue(classes);
	}

	public MenuModel getMenuModel() {
		return menuModel;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Block getBody() {
		return getResources().hasBody() ? getResources().getBody() : defaultBody;
	}

	protected void setupRender() {
		storeAndExecute(this, new SetupModel());
	}

	protected void cleanupRender() {
		storeAndExecute(this, new CleanupModel());
	}

	protected void setupModel() {
		getResources().getId();
		final List<String> menuItems = actions != null
				? Arrays.asList(actions.split(ACTIONS_DELIMITER))
				: Collections.<String>emptyList();
		menuModel = context != null ?
				modelSource.createMenuModel(getResources(), getOverrideMessages(), menuItems, context) :
				modelSource.createMenuModel(getResources(), getOverrideMessages(), menuItems);
		pushToEnvironment(MenuModel.class, menuModel);
	}

	protected void cleanupModel() {
		popFromEnvironment(MenuModel.class);
	}

	static class SetupModel implements ComponentAction<Menu> {
		public void execute(Menu component) {
			component.setupModel();
		}
	}

	static class CleanupModel implements ComponentAction<Menu> {
		public void execute(Menu component) {
			component.cleanupModel();
		}
	}
}
