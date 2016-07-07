package org.example.model

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Account extends BaseEntity {

    String getName();

    void setName(String name);

    Date getRegisteredAt();

    void setRegisteredAt(Date registeredAt);
}
