package org.example.hibernate

import org.example.model.BaseEntity
import org.greatage.domain.internal.AbstractEntity

import javax.persistence.Id
import javax.persistence.MappedSuperclass

/**
 * @author Ivan Khalopik
 */
@MappedSuperclass
public abstract class BaseEntityImpl extends AbstractEntity<Long> implements BaseEntity {

    @Id
    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    protected BaseEntityImpl() {
    }

    protected BaseEntityImpl(final Long id) {
        this.id = id;
    }
}