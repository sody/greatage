/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Resource {

	String getName();

	Resource getParent();

	Locale getLocale();

	boolean exists();

	Resource getChild(String string);

	Resource inLocale(Locale locale);

	InputStream open() throws IOException;

}
