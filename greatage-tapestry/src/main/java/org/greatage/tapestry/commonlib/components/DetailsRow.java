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
