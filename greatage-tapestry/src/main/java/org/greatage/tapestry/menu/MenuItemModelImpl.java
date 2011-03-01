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

package org.greatage.tapestry.menu;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.internal.TapestryInternalUtils;
import org.apache.tapestry5.ioc.Messages;

/**
 * @author Ivan Khalopik
 */
public class MenuItemModelImpl implements MenuItemModel {
	private final MenuModel model;
	private final String name;
	private Link link;
	private String label;

	public MenuItemModelImpl(MenuModel model, String name, Messages messages) {
		this.model = model;
		this.name = name;
		this.label = TapestryInternalUtils.defaultLabel(name, messages, name);
	}

	public String getName() {
		return name;
	}

	public String getLabel() {
		return label;
	}

	public Link getLink() {
		return link;
	}

	public MenuItemModel label(String label) {
		this.label = label;
		return this;
	}

	public MenuItemModel link(Link link) {
		this.link = link;
		return this;
	}

	public MenuModel model() {
		return model;
	}
}
