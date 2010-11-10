/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.grid;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.grid.GridModel;

/**
 * @author Ivan Khalopik
 */
public interface TableModel extends GridModel, ClientElement {

	PaginationModel getPaginationModel();

}
