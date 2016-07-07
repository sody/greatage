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

import org.example.model.Country

import javax.persistence.AttributeOverride
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity
@Table(name = "country")
@AttributeOverride(name = "id", column = @Column(name = "country_id"))
public class CountryImpl extends BaseEntityImpl implements Country {

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    public CountryImpl() {
    }

    public CountryImpl(final Long id) {
        super(id);
    }

    @Override
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
