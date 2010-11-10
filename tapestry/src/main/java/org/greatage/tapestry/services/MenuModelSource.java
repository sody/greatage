/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.services;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ioc.Messages;
import org.greatage.tapestry.menu.MenuModel;

import java.util.List;

/**
 * @author Ivan Khalopik
 */
public interface MenuModelSource {

	MenuModel createMenuModel(ComponentResources resources, Messages messages, List<String> menuItems, Object... context);

}
