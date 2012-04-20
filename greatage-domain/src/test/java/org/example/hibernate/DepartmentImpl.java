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

package org.example.hibernate;

import org.example.model.Company;
import org.example.model.Department;
import org.greatage.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity
@Table(name = "department")
public class DepartmentImpl extends AbstractEntity<Long> implements Department {

	@Id
	@Column(name = "department_id")
	private Long id;

	@Column(name = "name")
	private String name;

	@ManyToOne(targetEntity = CompanyImpl.class)
	@JoinColumn(name = "company_id")
	private Company company;

	public DepartmentImpl() {
	}

	public DepartmentImpl(final Long id, final String name) {
		this.id = id;
		this.name = name;
	}

	public DepartmentImpl(final Long id, final String name, final Company company) {
		this.id = id;
		this.name = name;
		this.company = company;
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
		this.company = company;
	}
}
