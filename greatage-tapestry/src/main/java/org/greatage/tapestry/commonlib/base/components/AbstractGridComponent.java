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
