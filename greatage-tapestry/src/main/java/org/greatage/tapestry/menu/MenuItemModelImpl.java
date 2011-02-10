/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
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
