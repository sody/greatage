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
