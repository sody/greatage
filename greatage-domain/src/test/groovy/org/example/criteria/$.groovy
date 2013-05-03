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

package org.example.criteria;

import org.greatage.domain.RootMapper;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class $ extends RootMapper {

    /**
     * Embeddable mappers
     */
    public static final CountryMapper country$ = new CountryMapper();
    public static final CompanyMapper company$ = new CompanyMapper();
    public static final CompanyInfoMapper companyInfo$ = new CompanyInfoMapper();
    public static final DepartmentMapper department$ = new DepartmentMapper();

    /**
     * Simple mappers
     */
    public static final SimpleCountryMapper simpleCountry$ = new SimpleCountryMapper();
    public static final SimpleCompanyMapper simpleCompany$ = new SimpleCompanyMapper();
    public static final SimpleCompanyInfoMapper simpleCompanyInfo$ = new SimpleCompanyInfoMapper();
    public static final SimpleDepartmentMapper simpleDepartment$ = new SimpleDepartmentMapper();

}
