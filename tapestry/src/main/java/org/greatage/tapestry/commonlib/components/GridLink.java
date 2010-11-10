/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.commonlib.components;

import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.base.AbstractLink;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ClientBehaviorSupport;
import org.apache.tapestry5.services.Request;
import org.greatage.tapestry.grid.TableModel;

/**
 * @author Ivan Khalopik
 */
public class GridLink extends AbstractLink {

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String event;

	@Parameter
	private Object context;

	@Parameter(value = "tableModel")
	private ComponentResources resources;

	@Parameter(value = "tableModel.clientId")
	private String zone;

	@Environmental
	private ClientBehaviorSupport clientBehaviorSupport;

	@Inject
	private Request request;

	@Environmental
	private TableModel tableModel;

	public TableModel getTableModel() {
		return tableModel;
	}

	protected void beginRender(MarkupWriter writer) {
		if (isDisabled()) {
			return;
		}

		final Link link = zone != null ?
				resources.createEventLink(event, context, zone) :
				resources.createEventLink(event, context);
		writeLink(writer, link);
		if (zone != null) {
			if (!request.isXHR()) {
				writer.getElement().forceAttributes(MarkupConstants.ONCLICK, MarkupConstants.WAIT_FOR_PAGE);
			}
			clientBehaviorSupport.linkZone(getClientId(), zone, link);
		}
	}

	protected void afterRender(MarkupWriter writer) {
		if (isDisabled()) {
			return;
		}
		writer.end();
	}

}
