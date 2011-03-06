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
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentAction;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Any;
import org.apache.tapestry5.corelib.mixins.NotEmpty;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.greatage.tapestry.commonlib.base.components.AbstractComponent;
import org.greatage.tapestry.menu.MenuItemModel;
import org.greatage.tapestry.menu.MenuModel;

/**
 * @author Ivan Khalopik
 */
@SupportsInformalParameters
public class MenuItem extends AbstractComponent {

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String name;

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String page;

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String event;

	@Parameter
	private boolean submitForm;

	@Parameter
	private boolean selected;

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String label;

	@Parameter
	private Object[] context;

	private MenuItemModel model;

	@Component(
			parameters = "class=prop:itemClass",
			inheritInformalParameters = true)
	@MixinClasses(NotEmpty.class)
	private Any border;

	@Inject
	private PageRenderLinkSource linkSource;

	@Inject
	private Block defaultBody;

	@Environmental
	private MenuModel menuModel;

	public MenuModel getMenuModel() {
		return menuModel;
	}

	public MenuItemModel getModel() {
		return model;
	}

	public boolean isWithLink() {
		return getModel().getLink() != null;
	}

	public String getItemClass() {
		return selected ? "selected" : null;
	}

	public Block getBody() {
		if (getResources().hasBody()) {
			return getResources().getBody();
		} else {
			final Block overrideBody = getOverrides().getOverrideBlock(name + "MenuItem");
			return overrideBody != null ? overrideBody : defaultBody;
		}
	}

	protected void setupRender() {
		if (name == null) {
			name = createName();
		}
		storeAndExecute(this, new SetupModel(name));
	}

	protected void setupModel(String name) {
		model = getMenuModel().getByName(name);
		if (model == null) {
			model = getMenuModel().add(name);
		}
		final Link link = context != null ? createLink(context) : createLink();
		if (link != null) {
			model.link(link);
		}
		if (label != null) {
			model.label(label);
		}
	}

	protected String createName() {
		final String name = page != null ? page : event != null ? event : null;
		return allocateClientId(name);
	}

	protected Link createLink(Object... context) {
		if (page != null) {
			return linkSource.createPageRenderLinkWithContext(page, context);
		} else if (event != null) {
			return submitForm ?
					getResources().createFormEventLink(event, context) :
					getResources().createEventLink(event, context);
		}
		return null;
	}

	static class SetupModel implements ComponentAction<MenuItem> {
		private final String name;

		SetupModel(String name) {
			this.name = name;
		}

		public void execute(MenuItem component) {
			component.setupModel(name);
		}
	}
}
