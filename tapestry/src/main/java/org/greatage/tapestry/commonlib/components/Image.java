/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.commonlib.components;

import org.apache.commons.codec.binary.Base64;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.greatage.tapestry.commonlib.base.components.AbstractComponent;

import java.io.UnsupportedEncodingException;

/**
 * @author Ivan Khalopik
 */
public class Image extends AbstractComponent {

	@Parameter(required = true)
	private byte[] source;

	@Parameter(defaultPrefix = BindingConstants.LITERAL, value = "image/jpeg")
	private String contentType;

	protected boolean beginRender(MarkupWriter writer) {
		if (source != null) {
			writer.element("img");
			final StringBuilder sb = new StringBuilder("data:");
			sb.append(contentType).append(";base64,");
			sb.append(serializeData(source));
			writer.attributes("src", sb.toString());
			writer.end();
		}
		return false;
	}

	private String serializeData(byte[] data) {
		try {
			final byte[] bytes = Base64.encodeBase64(data);
			return new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
}
