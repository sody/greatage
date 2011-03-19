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

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.PropertyModel;
import org.apache.tapestry5.grid.ColumnSort;
import org.greatage.tapestry.CSSConstants;
import org.greatage.tapestry.commonlib.base.components.AbstractGridComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 */
public class GridSorter extends AbstractGridComponent {

	private List<String> sortColumns;

	@Property
	private String sortColumn;

	@Component(
			parameters = {
					"overrides=overrides",
					"class=prop:sorterClass"},
			inheritInformalParameters = true)
	private Menu menu;

	@Component(
			parameters = {
					"overrides=overrides",
					"selected=current"})
	private MenuItem menuItem;

	@Component(
			parameters = {
					"event=sort",
					"context=sortColumn"})
	private GridLink sortLink;

	public List<String> getSortColumns() {
		if (sortColumns == null) {
			sortColumns = createSortColumns();
		}
		return sortColumns;
	}

	public boolean isCurrent() {
		return getSortModel().getColumnSort(sortColumn) != ColumnSort.UNSORTED;
	}

	public String getSorterClass() {
		return CSSConstants.TOOLBAR_MENU_CLASS;
	}

	@SuppressWarnings({"unchecked"})
	private List<String> createSortColumns() {
		final List<String> result = new ArrayList<String>();
		final List<String> propertyNames = getDataModel().getPropertyNames();
		for (String name : propertyNames) {
			final PropertyModel propertyModel = getDataModel().get(name);
			if (propertyModel.isSortable()) {
				result.add(propertyModel.getPropertyName());
			}
		}
		return result;
	}
}
