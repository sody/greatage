package org.greatage.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface EntityQuery<PK extends Serializable, E extends Entity<PK>, Q extends EntityQuery<PK, E, Q>> {

	Q setQueryString(String queryString);

	Q includeKeys(Collection<PK> keys);

	Q excludeKeys(Collection<PK> keys);

	int count();

	List<E> list(Pagination pagination);

	List<E> list();

	E unique();

}
