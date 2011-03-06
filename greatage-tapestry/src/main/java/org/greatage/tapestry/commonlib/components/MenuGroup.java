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

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.greatage.tapestry.commonlib.base.components.AbstractMenuComponent;

/**
 * @author Ivan Khalopik
 */
@Import(library = "menu.js")
@SupportsInformalParameters
public class MenuGroup extends AbstractMenuComponent {

	@Component(
			parameters = {
					"overrides=overrides",
					"id=prop:triggerId"},
			publishParameters = "label")
	private MenuItem menuItem;

}
