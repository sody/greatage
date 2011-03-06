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

package org.greatage.tapestry.commonlib.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.greatage.tapestry.CSSConstants;
import org.greatage.tapestry.commonlib.base.components.AbstractGridComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 */
@SupportsInformalParameters
public class GridSizer extends AbstractGridComponent {

	@Parameter(value = "2,10,50", defaultPrefix = BindingConstants.LITERAL)
	private String pages;

	private List<Integer> pageSizes;

	@Property
	private int pageSize;

	@Component(
			parameters = {
					"overrides=overrides",
					"class=prop:sizerClass"},
			inheritInformalParameters = true)
	private Menu menu;

	@Component(
			parameters = {
					"overrides=overrides",
					"selected=current"})
	private MenuItem menuItem;

	@Component(
			parameters = {
					"event=changePageSize",
					"context=pageSize",
					"disabled=current"})
	private GridLink pageSizeLink;

	public List<Integer> getPageSizes() {
		if (pageSizes == null) {
			pageSizes = createPageSizes();
		}
		return pageSizes;
	}

	public boolean isCurrent() {
		return pageSize == getPaginationModel().getPageSize();
	}

	public String getSizerClass() {
		return CSSConstants.TOOLBAR_MENU_CLASS;
	}

	protected List<Integer> createPageSizes() {
		final List<Integer> result = new ArrayList<Integer>();
		final String[] elements = pages.split(",");
		for (String element : elements) {
			result.add(Integer.parseInt(element));
		}
		return result;
	}
}