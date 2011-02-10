/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.commonlib.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.FormSupport;

/**
 * @author Ivan Khalopik
 */
@Import(library = "formsubmit.js")
public class FormSubmit {

	@Parameter(defaultPrefix = BindingConstants.LITERAL, value = "change")
	private String submitEvent;

	@InjectContainer
	private ClientElement container;

	@Environmental
	private FormSupport formSupport;

	@Inject
	private RenderSupport renderSupport;

	void afterRender(MarkupWriter writer) {
		renderSupport.addInit("formSubmit", formSupport.getClientId(), container.getClientId(), submitEvent);
	}

}
