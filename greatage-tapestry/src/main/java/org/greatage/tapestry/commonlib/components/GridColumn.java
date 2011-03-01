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

package org.greatage.tapestry.commonlib.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentAction;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.beaneditor.PropertyModel;
import org.apache.tapestry5.corelib.components.Any;
import org.apache.tapestry5.grid.ColumnSort;
import org.apache.tapestry5.internal.TapestryInternalUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.greatage.tapestry.CSSConstants;
import org.greatage.tapestry.commonlib.base.components.AbstractGridComponent;
import org.greatage.tapestry.grid.TableRowContext;

import java.util.List;

/**
 * @author Ivan Khalopik
 */
@SupportsInformalParameters
public class GridColumn extends AbstractGridComponent {
	@Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
	private String property;

	@Parameter
	private boolean sortable;

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String dataType;

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String label;

	@Parameter(name = "class", defaultPrefix = BindingConstants.LITERAL)
	private String columnClass;

	@Parameter
	private Block header;

	private PropertyModel model;

	private ColumnSort sort;

	@Environmental
	private TableRowContext context;

	@Inject
	private Block defaultHeader;

	@Inject
	private Block defaultBody;

	@Component(
			parameters = "class=prop:cellClass",
			inheritInformalParameters = true)
	private Any border;

	@Component(
			parameters = {
					"event=sort",
					"context=model.id",
					"disabled=!model.sortable"})
	private GridLink sortLink;

	@Component(
			parameters = {
					"model=model",
					"object=context.row",
					"editable=false",
					"overrides=overrides"})
	private BeanProperty beanProperty;

	public String getCellClass() {
		final List<String> classes = CollectionFactory.newList();
		if (columnClass != null) {
			classes.add(columnClass);
		}
		classes.add(getModel().getId());
		switch (sort) {
			case ASCENDING:
				classes.add(CSSConstants.GRID_ASCENDING_CLASS);
				break;
			case DESCENDING:
				classes.add(CSSConstants.GRID_DESCENDING_CLASS);
				break;
			default:
				if (model.isSortable()) {
					classes.add(CSSConstants.GRID_UNSORTED_CLASS);
				}
		}
		return TapestryInternalUtils.toClassAttributeValue(classes);
	}

	public PropertyModel getModel() {
		return model;
	}

	public TableRowContext getContext() {
		return context;
	}

	public Object getHeader() {
		return header != null ? header : defaultHeader;
	}

	public Object getBody() {
		return getResources().hasBody() ? getResources().getBody() : defaultBody;
	}

	protected void setupRender() {
		if (context.isHeader()) {
			storeAndExecute(this, new SetupModel());
		}
	}

	protected void setupModel() {
		try {
			model = getDataModel().get(property);
		} catch (RuntimeException e) {
			model = getDataModel().add(property, null);
		}
		sort = getSortModel().getColumnSort(model.getId());
		model.sortable(sortable);
		if (dataType != null) {
			model.dataType(dataType);
		}
		if (label != null) {
			model.label(label);
		}
	}

	static class SetupModel implements ComponentAction<GridColumn> {
		public void execute(GridColumn component) {
			component.setupModel();
		}
	}
}
