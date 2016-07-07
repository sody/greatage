package org.example.objectify

import com.googlecode.objectify.annotation.Entity
import com.googlecode.objectify.annotation.Id
import org.example.model.Company
import org.example.model.CompanyInfo
import org.greatage.domain.internal.AbstractEntity


/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = "company")
public class CompanyImpl extends AbstractEntity<Long> implements Company {

    @Id
    private Long id;

    private String name;
    private Date registeredAt;

    public CompanyImpl() {
    }

    public CompanyImpl(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Date getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(final Date registeredAt) {
        this.registeredAt = registeredAt;
    }

    @Override
    public CompanyInfo getInfo() {
        return null;
    }
}
