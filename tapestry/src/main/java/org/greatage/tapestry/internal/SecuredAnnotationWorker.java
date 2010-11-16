/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.internal;

import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.runtime.Component;
import org.apache.tapestry5.services.ClassTransformation;
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.apache.tapestry5.services.ComponentInstanceOperation;
import org.apache.tapestry5.services.TransformConstants;
import org.greatage.security.SecurityChecker;
import org.greatage.security.annotations.Authority;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SecuredAnnotationWorker implements ComponentClassTransformWorker {
	private final SecurityChecker securityChecker;

	public SecuredAnnotationWorker(final SecurityChecker securityChecker) {
		this.securityChecker = securityChecker;
	}

	public void transform(final ClassTransformation transformation, final MutableComponentModel model) {
		final Authority authority = transformation.getAnnotation(Authority.class);
		if (authority != null) {
			transformation.getOrCreateMethod(TransformConstants.DISPATCH_COMPONENT_EVENT).addOperationBefore(new ComponentInstanceOperation() {
				public void invoke(final Component instance) {
					securityChecker.checkAuthority(authority.value());
				}
			});
		}
	}
}
