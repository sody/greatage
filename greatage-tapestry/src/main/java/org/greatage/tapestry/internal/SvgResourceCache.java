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
