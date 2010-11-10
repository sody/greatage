/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.commonlib.components;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.greatage.tapestry.commonlib.base.components.AbstractComponent;

/**
 * @author Ivan Khalopik
 */
@SupportsInformalParameters
public class ViewStack extends AbstractComponent {

	@Parameter(required = true)
	private String state;

	protected Object beginRender() {
		if (state == null) {
			return getResources().getBody();
		}
		final Block block = getOverrides().getOverrideBlock(state);
		return block != null ? block : getResources().getBody();
	}

	protected boolean beforeRenderBody() {
		return false;
	}
}
