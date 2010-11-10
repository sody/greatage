/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.commonlib.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Mixin;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.internal.TapestryInternalUtils;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.greatage.tapestry.CSSConstants;
import org.greatage.tapestry.commonlib.base.components.AbstractComponent;
import org.greatage.tapestry.commonlib.mixins.DefineClass;

import java.util.List;

/**
 * @author Ivan Khalopik
 */
@SupportsInformalParameters
public class Box extends AbstractComponent {

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String title;

	@Mixin
	private DefineClass defineClass;

	public String getClassName() {
		final List<String> classes = CollectionFactory.newList();
		classes.add(CSSConstants.CONTAINER_CLASS);
		classes.add(CSSConstants.BOX_CLASS);
		return TapestryInternalUtils.toClassAttributeValue(classes);
	}

	protected void beginRender(MarkupWriter writer) {
		writer.element("fieldset", "class", getClassName());
		getResources().renderInformalParameters(writer);
		if (title != null) {
			writer.element("legend");
			writer.write(title);
			writer.end();
		}
	}

	protected void afterRender(MarkupWriter writer) {
		writer.end();
	}
}
