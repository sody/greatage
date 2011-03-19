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
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.corelib.components.Any;
import org.apache.tapestry5.internal.TapestryInternalUtils;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.greatage.tapestry.CSSConstants;
import org.greatage.tapestry.commonlib.base.components.AbstractComponent;

import java.util.List;

/**
 * @author Ivan Khalopik
 */
@SupportsInformalParameters
public class Panel extends AbstractComponent {
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String title;

	@Parameter(name = "class", defaultPrefix = BindingConstants.LITERAL)
	private String className;

	@Component(
			parameters = "class=prop:panelClass",
			inheritInformalParameters = true)
	private Any border;

	@Component(
			parameters = {
					"overrides=overrides",
					"class=prop:panelMenuClass"},
			publishParameters = "actions")
	private Menu panelMenu;

	public String getPanelClass() {
		final List<String> classes = CollectionFactory.newList();
		classes.add(CSSConstants.CONTAINER_CLASS);
		classes.add(CSSConstants.PANEL_CLASS);
		if (className != null) {
			classes.add(className);
		}
		return TapestryInternalUtils.toClassAttributeValue(classes);
	}

	public String getPanelBodyClass() {
		return createContainerClass(CSSConstants.PANEL_BODY_CLASS);
	}

	public String getPanelHeaderClass() {
		return createContainerClass(CSSConstants.PANEL_HEADER_CLASS);
	}

	public String getPanelTitleClass() {
		return createContainerClass(CSSConstants.PANEL_TITLE_CLASS);
	}

	public String getPanelMenuClass() {
		return CSSConstants.PANEL_MENU_CLASS;
	}

	public String getTitle() {
		return title;
	}

	private String createContainerClass(String additionalClass) {
		final List<String> classes = CollectionFactory.newList();
		classes.add(CSSConstants.CONTAINER_CLASS);
		classes.add(additionalClass);
		return TapestryInternalUtils.toClassAttributeValue(classes);
	}
}
