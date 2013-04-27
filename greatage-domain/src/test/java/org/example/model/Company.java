package org.example.model;

import org.greatage.domain.Entity;

import java.util.Date;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Company extends Entity<Long> {

    String getName();

    void setName(String name);

    Date getRegisteredAt();

    void setRegisteredAt(Date registeredAt);

}
