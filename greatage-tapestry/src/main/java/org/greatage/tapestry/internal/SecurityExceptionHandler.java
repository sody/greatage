/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.internal;

import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.RequestExceptionHandler;
import org.apache.tapestry5.services.ResponseRenderer;
import org.greatage.security.SecurityException;
import org.greatage.util.ReflectionUtils;
import org.slf4j.Logger;

import java.io.IOException;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SecurityExceptionHandler implements RequestExceptionHandler {
	private final RequestExceptionHandler delegate;
	private final ResponseRenderer renderer;
	private final ComponentClassResolver resolver;
	private final Class loginPage;

	private final Logger logger;

	public SecurityExceptionHandler(final RequestExceptionHandler delegate,
									final ResponseRenderer renderer,
									final ComponentClassResolver resolver,
									final Class loginPage,
									final Logger logger) {

		this.delegate = delegate;
		this.renderer = renderer;
		this.resolver = resolver;
		this.loginPage = loginPage;
		this.logger = logger;
	}

	@SuppressWarnings({"ThrowableResultOfMethodCallIgnored"})
	public void handleRequestException(final Throwable exception) throws IOException {
		final SecurityException securityException = ReflectionUtils.findException(exception, SecurityException.class);
		if (securityException != null) {
			final String pageName = resolver.resolvePageClassNameToPageName(loginPage.getName());
			renderer.renderPageMarkupResponse(pageName);
			logger.info(securityException.getMessage());
		} else {
			delegate.handleRequestException(exception);
		}
	}
}
