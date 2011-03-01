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

package org.greatage.tapestry.grid;

/**
 * @author Ivan Khalopik
 */
public class PaginationModelImpl implements PaginationModel {
	private int currentPage;
	private int pageSize;
	private int lastPage;
	private int size;
	private int startIndex;
	private int endIndex;

	public PaginationModelImpl(int currentPage, int pageSize, int size) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.size = size;
		update();
	}

	public int getFirstPage() {
		return 1;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getSize() {
		return size;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void updatePage(int page) {
		currentPage = page;
		update();
	}

	public void updatePageSize(int pageSize) {
		this.pageSize = pageSize;
		update();
	}

	public void update() {
		lastPage = ((size - 1) / pageSize) + 1;
		currentPage = Math.min(currentPage, lastPage);
		startIndex = (currentPage - 1) * pageSize;
		endIndex = Math.min(size - 1, startIndex + pageSize - 1);
	}
}
