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

package org.example.hibernate

import org.example.model.Company
import org.example.model.Department

import javax.persistence.*

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity
@Table(name = "department")
@AttributeOverride(name = "id", column = @Column(name = "department_id"))
public class DepartmentImpl extends BaseEntityImpl implements Department {

    @Column(name = "name")
    private String name;

    @ManyToOne(targetEntity = CompanyImpl.class)
    @JoinColumn(name = "company_id")
    private Company company;

    public DepartmentImpl() {
    }

    public DepartmentImpl(final Long id) {
        super(id);
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
        this.company = company;
    }
}
