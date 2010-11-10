/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.details;

import org.apache.tapestry5.beaneditor.BeanModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ivan Khalopik
 */
public abstract class BeanModelUtils {
	private static final String COLUMNS_DELIMITER = "\\s*,\\s*";

	public static void setColumns(final BeanModel<?> model, final String columns) {
		final List<String> propertyNames = model.getPropertyNames();
		final List<String> includeColumns = new ArrayList<String>();
		final List<String> addColumns = new ArrayList<String>();
		final String[] cols = columns.split(COLUMNS_DELIMITER);
		for (String column : cols) {
			if (propertyNames.contains(column)) {
				includeColumns.add(column);
			} else {
				addColumns.add(column);
			}
		}
		model.include(includeColumns.toArray(new String[]{""}));
		for (String column : addColumns) {
			model.add(column, null);
		}
		model.reorder(cols);
	}

	public static void setSortColumns(final BeanModel<?> model, final String sortColumns) {
		final List<String> propertyNames = model.getPropertyNames();
		final List<String> columns = Arrays.asList(sortColumns.split(COLUMNS_DELIMITER));
		for (String name : propertyNames) {
			model.get(name).sortable(columns.contains(name));
		}
	}
}
