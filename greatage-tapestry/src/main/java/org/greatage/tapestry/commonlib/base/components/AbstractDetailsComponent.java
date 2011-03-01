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

package org.greatage.tapestry.commonlib.base.components;

import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.greatage.tapestry.details.DetailsModel;

/**
 * @author Ivan Khalopik
 */
public abstract class AbstractDetailsComponent extends AbstractComponent {

	@Environmental
	private DetailsModel detailsModel;

	public BeanModel getDataModel() {
		return detailsModel.getDataModel();
	}

	public Object getObject() {
		return detailsModel.getObject();
	}

	public boolean isEditable() {
		return detailsModel.isEditable();
	}
}
