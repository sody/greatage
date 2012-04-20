package org.example.objectify;

import org.example.model.Company;
import org.greatage.domain.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

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
}
