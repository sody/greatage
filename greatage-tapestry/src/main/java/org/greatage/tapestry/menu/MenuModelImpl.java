/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
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
