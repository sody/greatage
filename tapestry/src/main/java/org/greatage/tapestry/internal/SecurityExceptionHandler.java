/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.internal;

import org.apache.tapestry5.ioc.internal.OperationException;
import org.apache.tapestry5.runtime.ComponentEventException;
import org.apache.tapestry5.services.RequestExceptionHandler;
import org.apache.tapestry5.services.ResponseRenderer;

import java.io.IOException;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SecurityExceptionHandler implements RequestExceptionHandler {
	private final RequestExceptionHandler delegate;
	private final ResponseRenderer renderer;

	public SecurityExceptionHandler(final RequestExceptionHandler delegate, final ResponseRenderer renderer) {
		this.delegate = delegate;
		this.renderer = renderer;
	}

	public void handleRequestException(final Throwable exception) throws IOException {
		if (needsRedirect(exception)) {
			renderer.renderPageMarkupResponse("security/login");
		}
		delegate.handleRequestException(exception);
	}

	private boolean needsRedirect(final Throwable exception) {
		if (exception instanceof SecurityException) {
			return true;
		}
		if (exception.getCause() != null &&
				(exception instanceof OperationException || exception instanceof ComponentEventException)) {
			return needsRedirect(exception.getCause());
		}
		return false;
	}

}
