package org.greatage.domain;

import java.io.Serializable;
import java.util.List;

/**
* @author Ivan Khalopik
* @since 1.0
*/
public interface Query<PK extends Serializable, E extends Entity<PK>> {

    Query<PK, E> filter(Criteria<PK, E> criteria);

    Query<PK, E> fetch(Property property);

    Query<PK, E> sort(Property property, boolean ascending, boolean ignoreCase);

    Query<PK, E> paginate(int start, int count);


    long count();

    List<E> list();

    List<PK> keys();

    E unique();

    interface Property {

		String getPath();

		String getProperty();
	}

    interface Criteria<PK extends Serializable, E extends Entity<PK>> {

		Criteria<PK, E> and(Criteria<PK, E> criteria);

		Criteria<PK, E> or(Criteria<PK, E> criteria);

		Criteria<PK, E> not();
	}
}
