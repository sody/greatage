/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.commonlib.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.internal.TapestryInternalUtils;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.greatage.tapestry.CSSConstants;
import org.greatage.tapestry.commonlib.base.components.AbstractComponent;

import java.util.List;

/**
 * @author Ivan Khalopik
 */
@SupportsInformalParameters
public class Container extends AbstractComponent {

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String element;

	@Parameter(name = "class", defaultPrefix = BindingConstants.LITERAL)
	private String className;

	protected String defaultElement() {
		return getResources().getElementName("div");
	}

	public String getClassName() {
		final List<String> classes = CollectionFactory.newList();
		classes.add(CSSConstants.CONTAINER_CLASS);
		if (className != null) {
			classes.add(className);
		}
		return TapestryInternalUtils.toClassAttributeValue(classes);
	}

	protected void beginRender(MarkupWriter writer) {
		writer.element(element, "class", getClassName());
		getResources().renderInformalParameters(writer);
	}

	protected void afterRender(MarkupWriter writer) {
		writer.end();
	}
}
