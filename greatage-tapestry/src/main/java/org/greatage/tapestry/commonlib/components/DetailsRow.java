/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.commonlib.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentAction;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.beaneditor.PropertyModel;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.tapestry.commonlib.base.components.AbstractDetailsComponent;

/**
 * @author Ivan Khalopik
 */
public class DetailsRow extends AbstractDetailsComponent {

	@Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
	private String property;

	@Parameter
	private Block header;

	@Parameter
	private Boolean editable;

	private PropertyModel model;

	@Inject
	private Block defaultHeader;

	@Inject
	private Block defaultBody;

	@Component(
			parameters = {
					"model=model",
					"object=object",
					"editable=editable",
					"overrides=overrides"},
			publishParameters = "validate,translate")
	private BeanProperty beanProperty;

	@Override
	public boolean isEditable() {
		return editable != null ? editable && super.isEditable() : super.isEditable();
	}

	public PropertyModel getModel() {
		return model;
	}

	public Object getHeader() {
		return header != null ? header : defaultHeader;
	}

	public Object getBody() {
		return getResources().hasBody() ? getResources().getBody() : defaultBody;
	}

	protected void setupRender() {
		storeAndExecute(this, new SetupModel());
	}

	protected void setupModel() {
		try {
			model = getDataModel().get(property);
		} catch (RuntimeException e) {
			model = getDataModel().add(property, null);
		}
	}

	static class SetupModel implements ComponentAction<DetailsRow> {
		public void execute(DetailsRow component) {
			component.setupModel();
		}
	}
}
