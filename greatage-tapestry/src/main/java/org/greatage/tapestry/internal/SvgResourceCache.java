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

package org.greatage.tapestry.internal;

import org.apache.tapestry5.internal.services.ResourceCache;
import org.apache.tapestry5.internal.services.StreamableResource;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.services.InvalidationListener;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class SvgResourceCache implements ResourceCache {
	private final ResourceCache delegate;

	public SvgResourceCache(final ResourceCache delegate) {
		this.delegate = delegate;
	}

	public boolean requiresDigest(final Resource resource) {
		return delegate.requiresDigest(resource);
	}

	public StreamableResource getStreamableResource(final Resource resource) {
		final StreamableResource streamableResource = delegate.getStreamableResource(resource);
		if (resource.getPath().endsWith(".svg")) {
			return new SvgStreamableResource(streamableResource);
		}
		return streamableResource;
	}

	public String getDigest(final Resource resource) {
		return delegate.getDigest(resource);
	}

	public long getTimeModified(final Resource resource) {
		return delegate.getTimeModified(resource);
	}

	public void addInvalidationListener(final InvalidationListener listener) {
		delegate.addInvalidationListener(listener);
	}
}
