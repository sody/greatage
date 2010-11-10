/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.commonlib.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.MixinAfter;
import org.apache.tapestry5.annotations.Parameter;

/**
 * @author Ivan Khalopik
 */
@MixinAfter
public class DefineClass {
	@Parameter(name = "class", defaultPrefix = BindingConstants.LITERAL)
	private String className;

	protected void beginRender(MarkupWriter writer) {
		if (className != null) {
			writer.getElement().addClassName(className);
		}
	}
}
