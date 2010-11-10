/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.commonlib.components;

import org.apache.tapestry5.ComponentAction;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Mixin;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.internal.TapestryInternalUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.services.ComponentDefaultProvider;
import org.greatage.tapestry.CSSConstants;
import org.greatage.tapestry.commonlib.base.components.AbstractGridComponent;
import org.greatage.tapestry.commonlib.mixins.DefineClass;
import org.greatage.tapestry.grid.PaginationModel;
import org.greatage.tapestry.grid.TableRowContext;

import java.util.List;

/**
 * @author Ivan Khalopik
 */
@SupportsInformalParameters
public class GridTable extends AbstractGridComponent implements TableRowContext {

	@Parameter(cache = false)
	private String rowClass;

	@Parameter
	private Object row;

	@Parameter
	private int rowIndex;

	@Parameter(name = "volatile")
	private boolean volatileState;

	@Parameter
	private ValueEncoder encoder;

	@Inject
	private ComponentDefaultProvider defaultProvider;

	@Mixin
	private DefineClass defineClass;

	protected ValueEncoder defaultEncoder() {
		return defaultProvider.defaultValueEncoder("row", getResources());
	}

	public Object getRow() {
		return row;
	}

	public boolean isHeader() {
		return rowIndex < 0;
	}

	public String getRowClass() {
		final List<String> classes = CollectionFactory.newList();
		if (rowClass != null) {
			classes.add(rowClass);
		}
		if (rowIndex == getPaginationModel().getStartIndex()) {
			classes.add(CSSConstants.GRID_FIRST_CLASS);
		}
		if (rowIndex == getPaginationModel().getEndIndex()) {
			classes.add(CSSConstants.GRID_LAST_CLASS);
		}
		return TapestryInternalUtils.toClassAttributeValue(classes);
	}

	protected void setupRender() {
		rowIndex = -1;
		storeAndExecute(this, new SetupDataSource());
	}

	protected void beginRender(MarkupWriter writer) {
		writer.element("table", "class", CSSConstants.GRID_CLASS);
		getResources().renderInformalParameters(writer);
	}

	protected void afterRender(MarkupWriter writer) {
		writer.end();
	}

	@SuppressWarnings({"unchecked"})
	protected boolean beforeRenderBody(MarkupWriter writer) {
		if (rowIndex < 0) {
			writer.element("thead");
		} else {
			if (getPaginationModel().getSize() <= 0) return false;

			if (rowIndex == getPaginationModel().getStartIndex()) {
				writer.element("tbody");
			}
			if (volatileState) {
				if (encoder == null) {
					getFormSupport().store(this, new SetupRow(rowIndex));
				} else {
					final String key = encoder.toClient(row);
					getFormSupport().store(this, new SetupEncodedRow(key));
				}
			}
			setupRow(rowIndex);
		}
		writer.element("tr", "class", getRowClass());
		storeAndExecute(this, new SetupRowContext());
		return true;
	}

	protected boolean afterRenderBody(MarkupWriter writer) {
		if (rowIndex >= 0 && getPaginationModel().getSize() <= 0) return true;

		storeAndExecute(this, new CleanupRowContext());
		writer.end();
		if (rowIndex < 0) {
			rowIndex = getPaginationModel().getStartIndex();
			writer.end();
		} else if (rowIndex == getPaginationModel().getEndIndex()) {
			writer.end();
			return true;
		} else {
			rowIndex++;
		}
		return false;
	}

	protected void setupDataSource() {
		final PaginationModel pagination = getPaginationModel();
		getDataSource().prepare(pagination.getStartIndex(), pagination.getEndIndex(), getSortModel().getSortConstraints());
	}

	private void setupRow(int rowIndex) {
		row = getDataSource().getRowValue(rowIndex);
	}

	private void setupEncodedRow(String clientValue) {
		row = encoder.toValue(clientValue);
		if (row == null) {
			throw new IllegalArgumentException(
					String.format("%s returned null for client value '%s'.", encoder, clientValue));
		}
	}

	protected void setupRowContext() {
		pushToEnvironment(TableRowContext.class, this);
	}

	protected void cleanupRowContext() {
		popFromEnvironment(TableRowContext.class);
	}

	static class SetupDataSource implements ComponentAction<GridTable> {
		public void execute(GridTable component) {
			component.setupDataSource();
		}
	}

	static class SetupRow implements ComponentAction<GridTable> {
		private final int rowIndex;

		public SetupRow(int rowIndex) {
			this.rowIndex = rowIndex;
		}

		public void execute(GridTable component) {
			component.setupRow(rowIndex);
		}
	}

	static class SetupEncodedRow implements ComponentAction<GridTable> {
		private final String clientValue;

		public SetupEncodedRow(String clientValue) {
			this.clientValue = clientValue;
		}

		public void execute(GridTable component) {
			component.setupEncodedRow(clientValue);
		}
	}

	static class SetupRowContext implements ComponentAction<GridTable> {
		public void execute(GridTable component) {
			component.setupRowContext();
		}
	}

	static class CleanupRowContext implements ComponentAction<GridTable> {
		public void execute(GridTable component) {
			component.cleanupRowContext();
		}
	}
}
