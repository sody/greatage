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

import org.greatage.domain.internal.JunctionCriteria;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class RootMapper {

    public static <PK extends Serializable, E extends Entity<PK>>
    Query.Criteria<PK, E> and(final Query.Criteria<PK, E>... criteria) {
        return new JunctionCriteria<PK, E>(JunctionCriteria.Operator.AND, Arrays.asList(criteria));
    }

    public static <PK extends Serializable, E extends Entity<PK>>
    Query.Criteria<PK, E> or(final Query.Criteria<PK, E>... criteria) {
        return new JunctionCriteria<PK, E>(JunctionCriteria.Operator.OR, Arrays.asList(criteria));
    }

    public static <PK extends Serializable, E extends Entity<PK>>
    Query.Criteria<PK, E> not(final Query.Criteria<PK, E> criteria) {
        return criteria.not();
    }
}
