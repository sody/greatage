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

import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.runtime.Component;
import org.apache.tapestry5.services.ClassTransformation;
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.apache.tapestry5.services.ComponentInstanceOperation;
import org.apache.tapestry5.services.TransformConstants;
import org.greatage.security.AccessDeniedException;
import org.greatage.security.Authentication;
import org.greatage.security.AuthorityConstants;
import org.greatage.security.SecurityContext;
import org.greatage.security.annotations.Allow;
import org.greatage.security.annotations.Deny;
import org.greatage.security.annotations.Operation;
import org.greatage.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SecuredAnnotationWorker implements ComponentClassTransformWorker {
	private final SecurityContext securityContext;

	public SecuredAnnotationWorker(final SecurityContext securityContext) {
		this.securityContext = securityContext;
	}

	public void transform(final ClassTransformation transformation, final MutableComponentModel model) {
		final Allow allow = transformation.getAnnotation(Allow.class);
		final Deny deny = transformation.getAnnotation(Deny.class);
		if (allow != null || deny != null) {
			transformation.getOrCreateMethod(TransformConstants.DISPATCH_COMPONENT_EVENT).addOperationBefore(new ComponentInstanceOperation() {
				public void invoke(final Component instance) {
					final List<String> authorities = getAuthorities();
					if (allow != null) {
						checkAllow(allow, authorities);
					}
					if (deny != null) {
						checkDeny(deny, authorities);
					}
				}
			});
		}
	}

	private void checkDeny(final Deny deny, final List<String> authorities) {
		if (deny.operation() == Operation.AND) {
			if (authorities.containsAll(Arrays.asList(deny.value()))) {
				throw new AccessDeniedException(String.format("Access denied for authorities: '%s'", authorities));
			}
		} else if (deny.operation() == Operation.OR) {
			for (String authority : deny.value()) {
				if (authorities.contains(authority)) {
					throw new AccessDeniedException(String.format("Access denied for authorities: '%s'", authorities));
				}
			}
		}
	}

	private void checkAllow(final Allow allow, final List<String> authorities) {
		if (allow.operation() == Operation.AND) {
			if (!authorities.containsAll(Arrays.asList(allow.value()))) {
				throw new AccessDeniedException(String.format("Access denied for authorities: '%s'", authorities));
			}
		} else if (allow.operation() == Operation.OR) {
			for (String authority : allow.value()) {
				if (authorities.contains(authority)) {
					return;
				}
			}
			throw new AccessDeniedException(String.format("Access denied for authorities: '%s'", authorities));
		}
	}

	private List<String> getAuthorities() {
		final Authentication user = securityContext.getCurrentUser();
		return user != null ? user.getAuthorities() : CollectionUtils.newList(AuthorityConstants.STATUS_ANONYMOUS);
	}
}
