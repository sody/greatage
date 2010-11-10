/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.commonlib.components;

import org.apache.tapestry5.ComponentAction;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.grid.GridSortModel;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;
import org.apache.tapestry5.services.ClientBehaviorSupport;
import org.apache.tapestry5.services.Request;
import org.greatage.tapestry.commonlib.base.components.AbstractComponent;
import org.greatage.tapestry.grid.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 */
public class Grid extends AbstractComponent implements TableModel {
	private static final int DEFAULT_PAGE = 1;
	private static final int DEFAULT_PAGE_SIZE = 10;

	@Parameter(required = true, allowNull = false)
	private GridDataSource source;

	@Parameter
	private boolean async;

	private String zone;

	@Persist
	private Integer currentPage;

	@Persist
	private Integer pageSize;

	@Persist
	private List<MultiColumnSortModel.SortEntry> sortEntries;

	private GridDataSource dataSource;
	private GridSortModel sortModel;
	private BeanModel dataModel;
	private PaginationModel paginationModel;

	@Inject
	private BeanModelSource modelSource;

	@Inject
	private Request request;

	@Environmental
	private ClientBehaviorSupport clientBehaviorSupport;

	public String getClientId() {
		return zone;
	}

	public PaginationModel getPaginationModel() {
		if (paginationModel == null) {
			paginationModel = createPaginationModel();
		}
		return paginationModel;
	}

	public BeanModel getDataModel() {
		if (dataModel == null) {
			dataModel = createBeanModel();
		}
		return dataModel;
	}

	public GridDataSource getDataSource() {
		if (dataSource == null) {
			dataSource = createDataSource();
		}
		return dataSource;
	}

	public GridSortModel getSortModel() {
		if (sortModel == null) {
			sortModel = createSortModel();
		}
		return sortModel;
	}

	protected void setupRender() {
		storeAndExecute(this, new SetupModel());
	}

	protected void beginRender(MarkupWriter writer) {
		if (async && zone == null && !request.isXHR()) {
			zone = allocateClientId();
			writer.element("div", "id", zone);
			clientBehaviorSupport.addZone(zone, null, "show");
		}
	}

	protected void afterRender(MarkupWriter writer) {
		if (zone != null && !request.isXHR()) {
			writer.end();
		}
	}

	protected void cleanupRender() {
		storeAndExecute(this, new CleanupModel());
	}

	protected void setupModel() {
		pushToEnvironment(TableModel.class, this);
	}

	protected void cleanupModel() {
		popFromEnvironment(TableModel.class);
	}

	protected GridDataSource createDataSource() {
		return new CachingDataSource(source);
	}

	protected GridSortModel createSortModel() {
		if (sortEntries == null) {
			sortEntries = new ArrayList<MultiColumnSortModel.SortEntry>();
		}
		return new MultiColumnSortModel(this, sortEntries, 2);
	}


	@SuppressWarnings({"unchecked"})
	protected BeanModel createBeanModel() {
		final Class rowType = getDataSource().getRowType();
		return modelSource.createDisplayModel(rowType, getOverrides().getOverrideMessages());
	}

	protected PaginationModel createPaginationModel() {
		if (currentPage == null) {
			currentPage = DEFAULT_PAGE;
		}
		if (pageSize == null) {
			pageSize = DEFAULT_PAGE_SIZE;
		}
		return new PaginationModelImpl(currentPage, pageSize, getDataSource().getAvailableRows()) {
			@Override
			public void update() {
				super.update();
				currentPage = getCurrentPage();
				pageSize = getPageSize();
			}
		};
	}

	protected void onChangePage(int page) {
		getPaginationModel().updatePage(page);
	}

	protected Object onChangePage(int page, String zone) {
		onChangePage(page);
		return onUpdateGrid(zone);
	}

	protected void onChangePageSize(int pageSize) {
		getPaginationModel().updatePageSize(pageSize);
	}

	protected Object onChangePageSize(int pageSize, String zone) {
		onChangePageSize(pageSize);
		return onUpdateGrid(zone);
	}

	protected void onSort(String columnId) {
		getSortModel().updateSort(columnId);
	}

	protected Object onSort(String columnId, String zone) {
		onSort(columnId);
		return onUpdateGrid(zone);
	}

	protected Object onUpdateGrid(String zone) {
		this.zone = zone;
		return this;
	}

	static class SetupModel implements ComponentAction<Grid> {
		public void execute(Grid component) {
			component.setupModel();
		}
	}

	static class CleanupModel implements ComponentAction<Grid> {
		public void execute(Grid component) {
			component.cleanupModel();
		}
	}
}
