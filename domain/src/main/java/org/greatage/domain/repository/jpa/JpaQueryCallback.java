/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository.jpa;

import javax.persistence.Query;

/**
 * @author Ivan Khalopik
 */
public interface JpaQueryCallback<R> {

	R doInQuery(Query query);

}
