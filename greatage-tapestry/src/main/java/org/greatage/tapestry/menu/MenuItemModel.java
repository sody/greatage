/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.menu;

import org.apache.tapestry5.Link;

/**
 * @author Ivan Khalopik
 */
public interface MenuItemModel {

	String getName();

	String getLabel();

	Link getLink();

	MenuItemModel label(String label);

	MenuItemModel link(Link link);

	MenuModel model();

}
