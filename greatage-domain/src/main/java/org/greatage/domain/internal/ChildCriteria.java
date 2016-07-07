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

package org.greatage.domain.internal;

import org.greatage.domain.Query;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ChildCriteria implements Query.Criteria {
    private final String path;
    private final String property;
    private final Query.Criteria criteria;

    public ChildCriteria(final String path, final Query.Criteria criteria) {
        this(path, null, criteria);
    }

    public ChildCriteria(final String path, final String property, final Query.Criteria criteria) {
        this.path = path;
        this.property = property;
        this.criteria = criteria;
    }

    public String getProperty() {
        return property;
    }

    public String getPath() {
        return path;
    }

    public Query.Criteria getCriteria() {
        return criteria;
    }
}
