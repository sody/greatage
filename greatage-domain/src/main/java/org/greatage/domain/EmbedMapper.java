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

import org.greatage.domain.internal.AllCriteria;
import org.greatage.domain.internal.ChildCriteria;

import java.io.Serializable;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class EmbedMapper<V> implements Query.Property {
    private final String path;
    private final String property;

    public EmbedMapper(final String path, final String property) {
        this.path = path;
        this.property = property;
    }

    public String getPath() {
        return path;
    }

    public String getProperty() {
        return property;
    }

    public AllCriteria all() {
        return new AllCriteria();
    }

    public Query.Criteria is(final Query.Criteria criteria) {
        return new ChildCriteria(path, property, criteria);
    }

    protected <V> PropertyMapper<V> property(final String property) {
        return new PropertyMapper<V>(path, toPath(this.property, property));
    }

    protected <V> EmbedMapper<V> embed(final String property) {
        return new EmbedMapper<V>(path, toPath(this.property, property));
    }

    protected <VPK extends Serializable, V extends Entity<VPK>>
    EntityMapper<VPK, V> entity(final String property) {
        return new EntityMapper<VPK, V>(path, toPath(this.property, property));
    }

    private String toPath(final String path, final String property) {
        return path != null ?
                property != null ?
                        path + "." + property :
                        path :
                property;
    }
}
