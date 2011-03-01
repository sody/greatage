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

package org.greatage.tapestry.grid;

import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.beaneditor.PropertyModel;
import org.apache.tapestry5.grid.ColumnSort;
import org.apache.tapestry5.grid.GridModel;
import org.apache.tapestry5.grid.GridSortModel;
import org.apache.tapestry5.grid.SortConstraint;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 */
public class MultiColumnSortModel implements GridSortModel {
	private final List<SortEntry> entries;
	private final GridModel gridModel;
	private final int depth;

	public MultiColumnSortModel(final GridModel gridModel, final List<SortEntry> entries, int depth) {
		this.gridModel = gridModel;
		this.entries = entries;
		this.depth = depth;
		verifyDepth();
	}

	public ColumnSort getColumnSort(final String columnId) {
		final SortEntry sortEntry = getSortEntry(columnId);
		return sortEntry == null ? ColumnSort.UNSORTED : sortEntry.isAscending() ? ColumnSort.ASCENDING : ColumnSort.DESCENDING;
	}

	public void updateSort(final String columnId) {
		final SortEntry sortEntry = getSortEntry(columnId);
		if (sortEntry != null) {
			sortEntry.toggle();
			entries.remove(sortEntry);
			entries.add(0, sortEntry);
		} else {
			entries.add(0, new SortEntry(columnId));
		}
		verifyDepth();
	}

	private void verifyDepth() {
		while (entries.size() > depth) {
			entries.remove(entries.size() - 1);
		}
	}

	public List<SortConstraint> getSortConstraints() {
		final List<SortConstraint> constraints = new ArrayList<SortConstraint>();
		for (SortEntry entry : entries) {
			constraints.add(createSortConstraint(entry));
		}
		return constraints;
	}

	public void clear() {
		entries.clear();
	}

	private SortEntry getSortEntry(final String columnId) {
		for (SortEntry entry : entries) {
			if (entry.getColumnId().equals(columnId)) {
				return entry;
			}
		}
		return null;
	}

	private SortConstraint createSortConstraint(final SortEntry entry) {
		final ColumnSort sort = entry.isAscending() ? ColumnSort.ASCENDING : ColumnSort.DESCENDING;
		final BeanModel dataModel = gridModel.getDataModel();
		PropertyModel propertyModel;
		try {
			propertyModel = dataModel.getById(entry.getColumnId());
		} catch (Exception e) {
			//todo: remove this
			propertyModel = dataModel.add(entry.getColumnId(), null);
		}
		return new SortConstraint(propertyModel, sort);
	}

	public static class SortEntry {
		private final String columnId;
		private boolean ascending;

		public SortEntry(final String columnId) {
			this.columnId = columnId;
		}

		public String getColumnId() {
			return columnId;
		}

		public boolean isAscending() {
			return ascending;
		}

		public void toggle() {
			ascending = !ascending;
		}
	}
}
