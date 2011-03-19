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

import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;
import org.greatage.ioc.scope.ScopeConstants;
import org.greatage.ioc.scope.Scope;
import org.greatage.ioc.scope.ScopeManager;

import java.io.IOException;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class ThreadCleanupFilter implements RequestFilter {
	private Scope threadScope;

	public ThreadCleanupFilter(final ScopeManager scopeManager) {
		this.threadScope = scopeManager.getScope(ScopeConstants.THREAD);
	}

	public boolean service(final Request request, final Response response, final RequestHandler handler) throws IOException {
		try {
			return handler.service(request, response);
		}
		finally {
			threadScope.cleanup();
		}
	}
}
