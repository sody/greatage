/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.internal;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ioc.Messages;
import org.greatage.tapestry.menu.MenuModel;
import org.greatage.tapestry.menu.MenuModelImpl;
import org.greatage.tapestry.services.MenuModelSource;

import java.util.List;

/**
 * @author Ivan Khalopik
 */
public class MenuModelSourceImpl implements MenuModelSource {

	public MenuModel createMenuModel(ComponentResources resources, Messages messages, List<String> menuItems, Object... context) {
		//todo: defence
//		Defense.notNull(resources, "resources");
//		Defense.notNull(messages, "messages");
//		Defense.notNull(menuItems, "menuItems");
//		Defense.notNull(context, "context");

		final MenuModel model = new MenuModelImpl(messages);
		for (String menuItem : menuItems) {
			if (menuItem.startsWith("_")) {
				final String name = menuItem.substring(1);
				model.add(name, resources.createFormEventLink(name, context));
			} else {
				model.add(menuItem, resources.createEventLink(menuItem, context));
			}
		}
		return model;
	}
}
