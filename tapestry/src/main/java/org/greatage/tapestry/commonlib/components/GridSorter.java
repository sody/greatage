/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
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
