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

package org.greatage.tapestry.internal;

import org.apache.tapestry5.internal.services.StreamableResource;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SvgStreamableResource implements StreamableResource {
	private final StreamableResource delegate;

	public SvgStreamableResource(final StreamableResource delegate) {
		this.delegate = delegate;
	}

	public String getContentType() throws IOException {
		return "image/svg+xml";
	}

	public int getSize(final boolean compress) throws IOException {
		return delegate.getSize(compress);
	}

	public InputStream getStream(final boolean compress) throws IOException {
		return delegate.getStream(compress);
	}

	public long getLastModified() throws IOException {
		return delegate.getLastModified();
	}
}
