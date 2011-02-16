package org.greatage.tapestry.internal;

import org.apache.tapestry5.internal.services.StreamableResource;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Ivan Khalopik
 * @since 1.1
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
