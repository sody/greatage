/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
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

import org.apache.tapestry5.ComponentAction;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Mixin;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;
import org.greatage.tapestry.CSSConstants;
import org.greatage.tapestry.commonlib.base.components.AbstractComponent;
import org.greatage.tapestry.commonlib.mixins.DefineClass;
import org.greatage.tapestry.details.DetailsModel;

/**
 * @author Ivan Khalopik
 */
@SupportsInformalParameters
public class Details extends AbstractComponent implements DetailsModel {
	@Parameter(required = true)
	private Object object;

	@Parameter
	private boolean editable;

	@Inject
	private BeanModelSource modelSource;

	@Mixin
	private DefineClass defineClass;

	private BeanModel dataModel;

	public BeanModel getDataModel() {
		return dataModel;
	}

	public Object getObject() {
		return object;
	}

	public boolean isEditable() {
		return editable;
	}

	protected boolean beforeRenderBody() {
		return object != null;
	}

	protected void setupRender() {
		storeAndExecute(this, new SetupModel());
	}

	protected void cleanupRender() {
		storeAndExecute(this, new CleanupModel());
	}

	protected void beginRender(MarkupWriter writer) {
		writer.element("div", "class", CSSConstants.DETAILS_CLASS);
		getResources().renderInformalParameters(writer);
	}

	protected void afterRender(MarkupWriter writer) {
		writer.end();
	}

	@SuppressWarnings({"unchecked"})
	protected void setupModel() {
		Class objectType = getResources().getBoundType("object");
		dataModel = isEditable() ?
				modelSource.createEditModel(objectType, getOverrides().getOverrideMessages()) :
				modelSource.createDisplayModel(objectType, getOverrides().getOverrideMessages());
		pushToEnvironment(DetailsModel.class, this);
	}

	protected void cleanupModel() {
		popFromEnvironment(DetailsModel.class);
	}

	static class SetupModel implements ComponentAction<Details> {
		public void execute(Details component) {
			component.setupModel();
		}
	}

	static class CleanupModel implements ComponentAction<Details> {
		public void execute(Details component) {
			component.cleanupModel();
		}
	}
}
