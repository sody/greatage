package org.example.model

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface CompanyInfo {

    String getCode();

    void setCode(final String code);

    Country getCountry();

    void setCountry(Country country);

    Address getAddress();
}
