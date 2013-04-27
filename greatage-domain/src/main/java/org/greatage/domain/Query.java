package org.greatage.domain;

import java.io.Serializable;
import java.util.List;

/**
* @author Ivan Khalopik
* @since 1.0
*/
public interface Query<PK extends Serializable, E extends Entity<PK>> {

    Query<PK, E> filter(Criteria<PK, E> criteria);

    Query<PK, E> filter(String filter, Object value);

    Query<PK, E> and();

    Query<PK, E> or();

    Query<PK, E> end();


    Query<PK, E> fetch(Property property);

    Query<PK, E> fetch(Property property, boolean fetch);


    Query<PK, E> sort(Property property);

    Query<PK, E> sort(Property property, boolean ascending);

    Query<PK, E> sort(Property property, boolean ascending, boolean ignoreCase);


    Query<PK, E> skip(int count);

    Query<PK, E> limit(int count);

    Query<PK, E> paginate(int start, int count);


    long count();

    E unique();

    E first();

    List<E> list();

    Iterable<E> iterate();

    List<PK> keys();

    Iterable<PK> iterateKeys();

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
