/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.grid;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 */
public class CenteredPagesBuilder implements PagesBuilder {
	private static final int DEFAULT_RANGE = 5;
	private final int range;

	public CenteredPagesBuilder() {
		this(DEFAULT_RANGE);
	}

	public CenteredPagesBuilder(int range) {
		this.range = range;
	}

	public List<Page> build(PaginationModel model) {
		//todo: defence
//		Defense.notNull(model, "model");
		final int currentPage = model.getCurrentPage();
		final int halfRange = range / 2;
		int startPage = currentPage - halfRange;
		if (startPage < model.getFirstPage()) {
			startPage = model.getFirstPage();
		}
		int endPage = startPage + (range - 1);
		if (endPage > model.getLastPage()) {
			endPage = model.getLastPage();
		}
		startPage = endPage - (range - 1);
		if (startPage < model.getFirstPage()) {
			startPage = model.getFirstPage();
		}
		final List<Page> pages = new ArrayList<Page>();
		pages.add(Page.first());
		pages.add(Page.previous());
		pages.add(Page.separator());
		for (int i = startPage; i <= endPage; i++) {
			pages.add(Page.page(i));
		}
		pages.add(Page.separator());
		pages.add(Page.next());
		pages.add(Page.last());

		return pages;
	}
}
