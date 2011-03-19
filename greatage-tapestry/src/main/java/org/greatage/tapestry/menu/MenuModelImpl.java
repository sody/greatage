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

package org.greatage.tapestry.menu;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;

import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 */
public class MenuModelImpl implements MenuModel {
	private final List<String> menuItems = CollectionFactory.newList();
	private final Map<String, MenuItemModel> items = CollectionFactory.newMap();
	private final Messages messages;

	public MenuModelImpl(Messages messages) {
		this.messages = messages;
	}

	public List<String> getNames() {
		return menuItems;
	}

	public MenuItemModel getByName(String name) {
		return items.get(name);
	}

	public MenuItemModel add(String name) {
		return add(name, null);
	}

	public MenuItemModel add(String name, Link link) {
		if (!items.containsKey(name)) {
			final MenuItemModel menuItemModel = new MenuItemModelImpl(this, name, messages).link(link);
			items.put(name, menuItemModel);
			menuItems.add(name);
		}
		return items.get(name);
	}
}
