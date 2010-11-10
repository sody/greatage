/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.grid;

/**
 * @author Ivan Khalopik
 */
public interface PaginationModel {

	int getFirstPage();

	int getCurrentPage();

	int getLastPage();

	int getPageSize();

	int getSize();

	int getStartIndex();

	int getEndIndex();

	void updatePage(int page);

	void updatePageSize(int pageSize);
}
