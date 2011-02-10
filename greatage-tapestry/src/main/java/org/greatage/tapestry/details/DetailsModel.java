/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.details;

import org.apache.tapestry5.beaneditor.BeanModel;

/**
 * @author Ivan Khalopik
 */
public interface DetailsModel {

	BeanModel getDataModel();

	Object getObject();

	boolean isEditable();

}
