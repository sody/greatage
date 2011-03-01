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
