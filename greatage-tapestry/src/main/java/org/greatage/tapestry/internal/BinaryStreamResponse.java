/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.internal;

import org.apache.tapestry5.ContentType;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.services.Response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Ivan Khalopik
 */
public class BinaryStreamResponse implements StreamResponse {
	private final ContentType contentType;
	private final InputStream inputStream;

	public BinaryStreamResponse(String contentType, byte[] inputStream) {
		this(contentType, new ByteArrayInputStream(inputStream)); //todo: defence
	}

	public BinaryStreamResponse(String contentType, InputStream inputStream) {
		this.inputStream = inputStream;
		//todo: defence
//		Defense.notBlank(contentType, "contentType");
//		Defense.notNull(inputStream, "inputStream");
		this.contentType = new ContentType(contentType);
	}

	public String getContentType() {
		return contentType.toString();
	}

	public InputStream getStream() throws IOException {
		return inputStream;
	}

	public void prepareResponse(Response response) {
		//do nothing
	}
}
