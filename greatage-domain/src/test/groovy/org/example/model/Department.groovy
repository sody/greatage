package org.example.model

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Department extends BaseEntity {

    String getName();

    void setName(String name);

    Company getCompany();

    void setCompany(Company company);
}
