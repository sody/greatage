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

import org.greatage.domain.EmbedMapper;
import org.greatage.domain.Entity;
import org.greatage.domain.EntityMapper;
import org.greatage.domain.PropertyMapper;
import org.greatage.domain.Query;

import java.io.Serializable;

/**
 * @author Ivan Khalopik
 */
public abstract class CompositeMapper implements Query.Property {
    private final String path;
    private final String property;

    protected CompositeMapper(final String path, final String property) {
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
        return new ChildCriteria(calculatePath(), calculateProperty(null), criteria);
    }

    protected <V> PropertyMapper<V> property(final String property) {
        return new PropertyMapper<V>(calculatePath(), calculateProperty(property));
    }

    protected <V> EmbedMapper<V> embed(final String property) {
        return new EmbedMapper<V>(calculatePath(), calculateProperty(property));
    }

    protected <VPK extends Serializable, V extends Entity<VPK>>
    EntityMapper<VPK, V> entity(final String property) {
        return new EntityMapper<VPK, V>(calculatePath(), calculateProperty(property));
    }

    protected String calculatePath() {
        return path;
    }

    protected String calculateProperty(final String property) {
        return property;
    }

    protected String join(final String path, final String property) {
        return path != null ?
                property != null ?
                        path + "." + property :
                        path :
                property;
    }
}
