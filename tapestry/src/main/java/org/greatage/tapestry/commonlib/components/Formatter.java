/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
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
