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

package org.example.criteria

import org.example.model.Company
import org.example.model.Department
import org.greatage.domain.EntityMapper
import org.greatage.domain.PropertyMapper

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SimpleDepartmentMapper extends EntityMapper<Long, Department> {
    public final PropertyMapper<String> name$ = property("name");
    public final EntityMapper<Long, Company> company$ = entity("company");

    /**
     * Root criteria.
     */
    SimpleDepartmentMapper() {
        super(null, null);
    }
}
