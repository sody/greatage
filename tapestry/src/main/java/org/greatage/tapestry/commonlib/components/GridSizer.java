/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
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