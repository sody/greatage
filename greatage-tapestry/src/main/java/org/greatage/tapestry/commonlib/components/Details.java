/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
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
