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
public class Page {
	private final PageType type;
	private final int value;

	private Page(PageType type) {
		this(type, -1);
	}

	private Page(PageType type, int value) {
		this.type = type;
		this.value = value;
	}

	public PageType getType() {
		return type;
	}

	public int getValue(PaginationModel model) {
		switch (type) {
			case PREVIOUS:
				return model.getCurrentPage() - 1;
			case NEXT:
				return model.getCurrentPage() + 1;
			case FIRST:
				return model.getFirstPage();
			case LAST:
				return model.getLastPage();
			default:
				return value;
		}
	}

	public boolean isDisabled(PaginationModel model) {
		final int val = getValue(model);
		return val > model.getLastPage() || val < model.getFirstPage() || val == model.getCurrentPage();
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder("Page[");
		if (type == PageType.PAGE) {
			builder.append(value);
		} else {
			builder.append(type);
		}
		builder.append("]");
		return builder.toString();
	}

	public static Page page(int i) {
		return new Page(PageType.PAGE, i);
	}

	public static Page previous() {
		return new Page(PageType.PREVIOUS);
	}

	public static Page next() {
		return new Page(PageType.NEXT);
	}

	public static Page first() {
		return new Page(PageType.FIRST);
	}

	public static Page last() {
		return new Page(PageType.LAST);
	}

	public static Page separator() {
		return new Page(PageType.SEPARATOR);
	}

	public enum PageType {
		PAGE,
		PREVIOUS,
		NEXT,
		FIRST,
		LAST,
		SEPARATOR
	}
}
