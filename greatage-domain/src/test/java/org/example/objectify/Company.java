package org.example.objectify;

import org.greatage.domain.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = "company")
public class Company extends AbstractEntity<Long> {

	@Id
	private Long id;

	private String name;
	private Date registeredAt;

	public Company() {
	}

	public Company(final Long id, final String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
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
