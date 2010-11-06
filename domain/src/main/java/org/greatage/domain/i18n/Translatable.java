/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.i18n;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author Ivan Khalopik
 */
public interface Translatable extends Serializable {

	String getTranslationType();

	String getTranslationCode();

	Collection<String> getTranslationVariants();

}