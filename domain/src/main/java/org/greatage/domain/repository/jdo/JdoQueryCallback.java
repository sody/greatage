/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository.jdo;

import javax.jdo.Query;

/**
 * @author Ivan Khalopik
 */
public interface JdoQueryCallback<R> {

	R doInQuery(Query query);

}
