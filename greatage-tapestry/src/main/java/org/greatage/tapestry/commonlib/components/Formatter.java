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
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.Messages;
import org.greatage.tapestry.commonlib.base.components.AbstractComponent;

/**
 * @author Ivan Khalopik
 */
public class Formatter extends AbstractComponent {
	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
	private String format;

	@Parameter
	private Object[] context;

	@Parameter
	private boolean raw;

	@Parameter(value = "overrideMessages")
	private Messages messages;

	protected boolean beginRender(MarkupWriter writer) {
		final String message = context != null ? messages.format(format, context) : messages.get(format);
		if (raw) {
			writer.writeRaw(message);
		} else {
			writer.write(message);
		}
		return false;
	}

	final void inject(Messages messages, String format, boolean raw, Object... context) {
		this.messages = messages;
		this.format = format;
		this.raw = raw;
		this.context = context;
	}
}
