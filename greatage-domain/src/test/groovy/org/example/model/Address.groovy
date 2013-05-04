package org.example.model

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Address {

    String getCity();

    void setCity(final String code);

    String getAddress1();

    void setAddress1(String address);

    String getAddress2();

    void setAddress2(String address);

    Country getCountry();

    void setCountry(Country country);
}
