package org.example.model;

import org.greatage.domain.Entity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Department extends Entity<Long> {

    String getName();

    void setName(String name);

    Company getCompany();

    void setCompany(Company company);
}
