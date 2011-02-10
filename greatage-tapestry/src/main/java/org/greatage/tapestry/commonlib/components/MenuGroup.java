/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.commonlib.components;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.greatage.tapestry.commonlib.base.components.AbstractMenuComponent;

/**
 * @author Ivan Khalopik
 */
@Import(library = "menu.js")
@SupportsInformalParameters
public class MenuGroup extends AbstractMenuComponent {

	@Component(
			parameters = {
					"overrides=overrides",
					"id=prop:triggerId"},
			publishParameters = "label")
	private MenuItem menuItem;

}
