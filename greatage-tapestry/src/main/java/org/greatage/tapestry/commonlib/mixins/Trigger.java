/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.commonlib.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * todo: move to components
 *
 * @author Ivan Khalopik
 */
@IncludeJavaScriptLibrary("trigger.js")
public class Trigger {

	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL, allowNull = false)
	private String content;

	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL, allowNull = false)
	private String trigger;

	@Inject
	private RenderSupport renderSupport;

	protected void afterRender(MarkupWriter writer) {
		renderSupport.addInit("trigger", trigger, content);
	}

}
