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

package org.greatage.tapestry.commonlib.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Mixin;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.internal.TapestryInternalUtils;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.greatage.tapestry.CSSConstants;
import org.greatage.tapestry.commonlib.base.components.AbstractComponent;
import org.greatage.tapestry.commonlib.mixins.DefineClass;

import java.util.List;

/**
 * @author Ivan Khalopik
 */
@SupportsInformalParameters
public class Box extends AbstractComponent {

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String title;

	@Mixin
	private DefineClass defineClass;

	public String getClassName() {
		final List<String> classes = CollectionFactory.newList();
		classes.add(CSSConstants.CONTAINER_CLASS);
		classes.add(CSSConstants.BOX_CLASS);
		return TapestryInternalUtils.toClassAttributeValue(classes);
	}

	protected void beginRender(MarkupWriter writer) {
		writer.element("fieldset", "class", getClassName());
		getResources().renderInformalParameters(writer);
		if (title != null) {
			writer.element("legend");
			writer.write(title);
			writer.end();
		}
	}

	protected void afterRender(MarkupWriter writer) {
		writer.end();
	}
}
