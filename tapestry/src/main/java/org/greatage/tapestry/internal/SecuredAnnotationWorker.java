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
import org.greatage.security.AccessDeniedException;
import org.greatage.security.PermissionResolver;
import org.greatage.security.annotations.Secured;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SecuredAnnotationWorker implements ComponentClassTransformWorker {
	private final PermissionResolver permissionResolver;

	public SecuredAnnotationWorker(final PermissionResolver permissionResolver) {
		this.permissionResolver = permissionResolver;
	}

	public void transform(final ClassTransformation transformation, final MutableComponentModel model) {
		final Secured secured = transformation.getAnnotation(Secured.class);
		if (secured != null) {
			transformation.getOrCreateMethod(TransformConstants.DISPATCH_COMPONENT_EVENT).addOperationBefore(new ComponentInstanceOperation() {
				public void invoke(final Component instance) {
					final String pageName = instance.getComponentResources().getPageName();
					final boolean granted = permissionResolver.isGranted(pageName, secured.value());
					if (!granted) {
						throw new AccessDeniedException(String.format("Access denied to page '%s'", pageName));
					}
				}
			});
		}
	}
}
