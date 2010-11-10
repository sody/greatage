/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.menu;

import org.apache.tapestry5.Link;

import java.util.List;

/**
 * @author Ivan Khalopik
 */
public interface MenuModel {

	List<String> getNames();

	MenuItemModel getByName(String id);

	MenuItemModel add(String id);

	MenuItemModel add(String id, Link link);

}
