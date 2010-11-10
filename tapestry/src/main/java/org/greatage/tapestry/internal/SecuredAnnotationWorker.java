/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.internal;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.runtime.Component;
import org.apache.tapestry5.runtime.ComponentEvent;
import org.apache.tapestry5.services.ClassTransformation;
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.apache.tapestry5.services.ComponentEventHandler;
import org.greatage.security.acl.AccessDeniedException;
import org.greatage.security.annotations.Secured;
import org.greatage.security.context.PermissionResolver;

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
		final Secured annotation = transformation.getAnnotation(Secured.class);
		if (annotation != null) {
			transformation.addComponentEventHandler(EventConstants.ACTIVATE, 0, "Page activation security checker",
					new ComponentEventHandler() {
						public void handleEvent(final Component instance, final ComponentEvent event) {
							final String pageName = instance.getComponentResources().getPageName();
							final boolean granted = permissionResolver.isGranted(pageName, annotation.value());
							if (!granted) {
								throw new AccessDeniedException(String.format("Access denied to page '%s'", pageName));
							}
						}
					});
		}
	}
}
