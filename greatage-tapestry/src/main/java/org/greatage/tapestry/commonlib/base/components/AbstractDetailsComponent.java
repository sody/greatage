/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
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
