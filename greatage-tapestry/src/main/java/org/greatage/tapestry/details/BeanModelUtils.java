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
