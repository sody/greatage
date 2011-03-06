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
