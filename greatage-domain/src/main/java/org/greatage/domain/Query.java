/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.greatage.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Query<PK extends Serializable, E extends Entity<PK>> {

    Query<PK, E> filter(Criteria criteria);

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

    interface Criteria {

        @Deprecated
        Criteria not();
    }
}
