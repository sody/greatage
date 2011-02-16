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
