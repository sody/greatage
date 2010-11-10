/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.commonlib.base.components;

import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.grid.GridSortModel;
import org.greatage.tapestry.grid.PaginationModel;
import org.greatage.tapestry.grid.TableModel;

/**
 * @author Ivan Khalopik
 */
public abstract class AbstractGridComponent extends AbstractComponent {

	@Environmental
	private TableModel tableModel;

	public PaginationModel getPaginationModel() {
		return tableModel.getPaginationModel();
	}

	public BeanModel getDataModel() {
		return tableModel.getDataModel();
	}

	public GridDataSource getDataSource() {
		return tableModel.getDataSource();
	}

	public GridSortModel getSortModel() {
		return tableModel.getSortModel();
	}
}
