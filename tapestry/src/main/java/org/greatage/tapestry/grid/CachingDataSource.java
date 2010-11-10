/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.grid;

import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.grid.SortConstraint;

import java.util.List;

/**
 * @author Ivan Khalopik
 */
public class CachingDataSource implements GridDataSource {
	private final GridDataSource delegate;
	private Integer availableRows;

	public CachingDataSource(GridDataSource delegate) {
		this.delegate = delegate; //todo: defence
	}

	public int getAvailableRows() {
		if (availableRows == null) {
			availableRows = delegate.getAvailableRows();
		}
		return availableRows;
	}

	public void prepare(int startIndex, int endIndex, List<SortConstraint> sortConstraints) {
		delegate.prepare(startIndex, endIndex, sortConstraints);
	}

	public Object getRowValue(int index) {
		return delegate.getRowValue(index);
	}

	public Class getRowType() {
		return delegate.getRowType();
	}
}
