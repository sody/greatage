/*
 * Copyright 2011 Ivan Khalopik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
