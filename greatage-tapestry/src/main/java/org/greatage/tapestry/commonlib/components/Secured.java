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

package org.greatage.tapestry.commonlib.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.Authentication;
import org.greatage.security.AuthorityConstants;
import org.greatage.security.SecurityContext;
import org.greatage.tapestry.commonlib.base.components.AbstractComponent;
import org.greatage.util.CollectionUtils;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Secured extends AbstractComponent {

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String allow;

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String deny;

	@Inject
	private SecurityContext securityContext;

	@SuppressWarnings({"RedundantIfStatement"})
	boolean setupRender() {
		final List<String> authorities = getAuthorities();
		if (allow != null && !authorities.contains(allow)) {
			return false;
		}
		if (deny != null && authorities.contains(deny)) {
			return false;
		}
		return true;
	}

	private List<String> getAuthorities() {
		final Authentication user = securityContext.getCurrentUser();
		return user != null ? user.getAuthorities() : CollectionUtils.newList(AuthorityConstants.STATUS_ANONYMOUS);
	}

}
