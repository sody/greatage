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

package org.example.objectify

import com.googlecode.objectify.annotation.Entity
import com.googlecode.objectify.annotation.Id
import org.example.model.Company
import org.example.model.Department
import org.greatage.domain.internal.AbstractEntity


/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = "department")
public class DepartmentImpl extends AbstractEntity<Long> implements Department {

    @Id
    private Long id;

    private String name;

    private CompanyImpl company;

    public DepartmentImpl() {
    }

    public DepartmentImpl(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(final Company company) {
        this.company = (CompanyImpl) company;
    }

    public void setCompany(final CompanyImpl company) {
        this.company = company;
    }
}
